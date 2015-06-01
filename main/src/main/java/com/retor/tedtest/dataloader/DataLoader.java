package com.retor.tedtest.dataloader;


import android.support.v4.app.FragmentActivity;
import com.parser.beans.Channel;
import com.parser.exceptions.LoaderException;
import com.parser.exceptions.ParserException;
import com.parser.loader.TedRssLoader;
import com.parser.parser.TedRssParser;
import com.parser.worker.IWorker;
import com.parser.worker.MainWorker;
import com.retor.tedtest.main.interfaces.IView;
import org.w3c.dom.Document;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.observables.AndroidObservable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * Created by retor on 05.05.2015.
 */
public class DataLoader implements IModel {
    private IView<Channel> view;
    private FragmentActivity activity;
    private ArrayList<Channel> items;
    private IWorker<Channel, Document> worker;
    private Observer<List<Channel>> observer;

    public DataLoader(Observer<List<Channel>> observer, FragmentActivity activity) {
        this.observer = observer;
        this.activity = activity;
        this.worker = new MainWorker(new TedRssLoader(), new TedRssParser());
//        this.worker = new TestWorker();
    }

    @Override
    public void getData(String... url) {
        this.items = new ArrayList<>();
        mainRequest(url).debounce(30, TimeUnit.SECONDS).subscribe(observer);
    }

    private Observable<List<Channel>> mainRequest(String... url) {
        return AndroidObservable.bindActivity(activity, Observable.from(url).flatMap(new Func1<String, Observable<Channel>>() {
            @Override
            public Observable<Channel> call(String s) {
                return getChannel(s).observeOn(Schedulers.io()).subscribeOn(Schedulers.from(Executors.newCachedThreadPool()));
            }
        })).subscribeOn(Schedulers.io()).buffer(4);
    }

    private Observable<Channel> getChannel(final String url) {
        return Observable.create(new Observable.OnSubscribe<Channel>() {
            @Override
            public void call(Subscriber<? super Channel> subscriber) {
                try {
                    subscriber.onNext(worker.getSingleData(url));
                } catch (LoaderException | ParserException e) {
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        });
    }
}

