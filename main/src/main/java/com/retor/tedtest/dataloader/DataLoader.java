package com.retor.tedtest.dataloader;


import android.support.v4.app.FragmentActivity;
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
import rx.schedulers.Schedulers;

import java.util.ArrayList;


/**
 * Created by retor on 05.05.2015.
 */
public class DataLoader implements IPresenter, Observer<ArrayList<Channel>> {
    private IView<Channel> view;
    private FragmentActivity activity;
    private ArrayList<Channel> items;

    public DataLoader(IView<Channel> view, FragmentActivity activity) {
        this.view = view;
        this.activity = activity;
    }

    public DataLoader(IView<Channel> view) {
        this.view = view;
    }

    @Override
    public void getData(final String... url) {
        AndroidObservable.bindActivity(activity, Observable.create(new Observable.OnSubscribe<ArrayList<Channel>>() {
            @Override
            public void call(Subscriber<? super ArrayList<Channel>> subscriber) {
                IWorker<Channel> worker = new MainWorker(new RssLoader(), new Parser());
                final ArrayList<Channel> out = new ArrayList<>();
                try {
                    out.addAll(worker.getData(url));
                } catch (LoaderException | ParserException e) {
                    e.printStackTrace();
                }
                subscriber.onNext(out);
                subscriber.onCompleted();
            }
        }))
                .subscribeOn(Schedulers.io()).subscribe(this);
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

