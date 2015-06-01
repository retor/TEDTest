package com.retor.tedtest.dataloader;

import android.support.v4.app.FragmentActivity;
import com.parser.beans.Channel;
import com.parser.exceptions.LoaderException;
import com.parser.exceptions.ParserException;
import com.parser.loader.ILoader;
import com.parser.loader.TedRssLoader;
import com.parser.parser.IParser;
import com.parser.parser.TedRssParser;
import org.w3c.dom.Document;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.observables.AndroidObservable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created by Admin on 26.05.15.
 */
public class SimpleRxModel implements IObservableModel<List<Channel>> {
    private Observer<List<Channel>> observer;
    private FragmentActivity activity;
    private IParser<Channel> parser;
    private ILoader<Document> loader;

    public SimpleRxModel(FragmentActivity activity, Observer<List<Channel>> observer) {
        this.activity = activity;
        this.observer = observer;
    }

    private Observable<Channel> getChannel(final String url) {
        return Observable.just(url).flatMap(new Func1<String, Observable<Document>>() {
            @Override
            public Observable<Document> call(String s) {
                return getResponse(url).onExceptionResumeNext(Observable.<Document>empty());
            }
        }).flatMap(new Func1<Document, Observable<Channel>>() {
            @Override
            public Observable<Channel> call(Document document) {
                return parseDocument(document, url).map(new Func1<Channel, Channel>() {
                    @Override
                    public Channel call(Channel channel) {
                        Collections.reverse(channel.getItems());
                        return channel;
                    }
                });
            }
        });
    }

    private Observable<Channel> parseDocument(final Document doc, final String url) {
        parser = new TedRssParser();
        return Observable.create(new Observable.OnSubscribe<Channel>() {
            @Override
            public void call(Subscriber<? super Channel> subscriber) {
                try {
                    Channel out = parser.getItem(doc.getElementsByTagName("channel").item(0));
                    out.setRssUrl(url);
                    subscriber.onNext(out);
                } catch (ParserException e) {
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        });
    }

    private Observable<Document> getResponse(final String url) {
        loader = new TedRssLoader();
        return Observable.create(new Observable.OnSubscribe<Document>() {
            @Override
            public void call(Subscriber<? super Document> subscriber) {
                try {
                    subscriber.onNext(loader.getResponse(url));
                } catch (LoaderException e) {
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public void getData(String... urls) {
        Observable<List<Channel>> ob = AndroidObservable.bindActivity(activity, Observable.from(urls).flatMap(new Func1<String, Observable<Channel>>() {
            @Override
            public Observable<Channel> call(String s) {
                return getChannel(s).observeOn(Schedulers.io()).subscribeOn(Schedulers.from(Executors.newCachedThreadPool()));
            }
        }).subscribeOn(Schedulers.io()).buffer(10));
        ob.subscribe(observer);
    }

    @Override
    public void setObserver(Observer<List<Channel>> observer) {
        this.observer = observer;
    }
}
