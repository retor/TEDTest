package com.retor.tedtest.dataloader;


import android.support.v4.app.FragmentActivity;
import android.util.Log;
import com.parser.beans.Channel;
import com.parser.exceptions.LoaderException;
import com.parser.exceptions.ParserException;
import com.parser.loader.RssLoader;
import com.parser.parser.Parser;
import com.parser.worker.IWorker;
import com.parser.worker.MainWorker;
import com.retor.tedtest.main.interfaces.IView;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.observables.AndroidObservable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import java.util.ArrayList;


/**
 * Created by retor on 05.05.2015.
 */
public class DataLoader implements IPresenter, Observer<ArrayList<Channel>>  {
    private IView<Channel> view;
    private FragmentActivity activity;
    private ArrayList<Channel> items;
    private IWorker<Channel> worker;
    private Observer<ArrayList<Channel>> observer;

    public DataLoader(IView<Channel> view, FragmentActivity activity) {
        this.view = view;
        this.activity = activity;
        this.observer = this;
        this.worker = new MainWorker(new RssLoader(), new Parser());
    }

    public DataLoader(Observer<ArrayList<Channel>> observer, FragmentActivity activity) {
        this.observer = observer;
        this.activity = activity;
        this.worker = new MainWorker(new RssLoader(), new Parser());
    }

    public DataLoader(IView<Channel> view) {
        this.view = view;
    }

    @Override
    public void getData(final String... url) {

        AndroidObservable.bindActivity(activity, Observable.create(new Observable.OnSubscribe<ArrayList<Channel>>() {
            @Override
            public void call(final Subscriber<? super ArrayList<Channel>> subscriber) {
                final ArrayList<Channel> out = new ArrayList<>();
                Observable.from(url).flatMap(new Func1<String, Observable<Channel>>() {
                    @Override
                    public Observable<Channel> call(final String s) {
                        return getChannel(s);
                    }
                }).subscribe(new Action1<Channel>() {
                    @Override
                    public void call(Channel channel) {
                        out.add(channel);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        subscriber.onError(throwable);
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        subscriber.onNext(out);
                    }
                });
                subscriber.onCompleted();
            }
        })).subscribeOn(Schedulers.io()).subscribe(observer);
    }

    private Observable<Channel> getChannel(final String url){
        return Observable.create(new Observable.OnSubscribe<Channel>() {
            @Override
            public void call(Subscriber<? super Channel> subscriber) {
                try {
                    subscriber.onNext(worker.getSingleData(url));
                    Log.d("Thread id", " " + Thread.currentThread().getId());
                } catch (LoaderException | ParserException e) {
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public void onCompleted() {
        if (items.size() > 1)
            view.loadData(items);
        else
            view.loadItem(items.get(0));
    }

    @Override
    public void onError(Throwable e) {
        view.onError(e);
    }

    @Override
    public void onNext(ArrayList<Channel> channels) {
        items = channels;
    }
}

