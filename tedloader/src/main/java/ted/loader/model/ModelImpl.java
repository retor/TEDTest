package ted.loader.model;

import it.sauronsoftware.feed4j.FeedIOException;
import it.sauronsoftware.feed4j.FeedParser;
import it.sauronsoftware.feed4j.FeedXMLParseException;
import it.sauronsoftware.feed4j.UnsupportedFeedException;
import it.sauronsoftware.feed4j.bean.Feed;
import it.sauronsoftware.feed4j.bean.FeedItem;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import ted.loader.TedNews;
import ted.loader.interfaces.IModel;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by retor on 05.05.2015.
 */
public class ModelImpl implements IModel<TedNews> {
    @Override
    public Observable<ArrayList<TedNews>> getData(final String url) {
        final Observable<Feed> observable = Observable.create(new Observable.OnSubscribe<Feed>() {
            @Override
            public void call(Subscriber<? super Feed> subscriber) {
                    try {
                        subscriber.onNext(getAll(url));
                    } catch (MalformedURLException e) {
                        subscriber.onError(e);
                    } catch (FeedIOException e) {
                        subscriber.onError(e);
                    } catch (UnsupportedFeedException e) {
                        subscriber.onError(e);
                    } catch (FeedXMLParseException e) {
                        subscriber.onError(e);
                    }
                subscriber.onCompleted();
            }
        });
        return Observable.create(new Observable.OnSubscribe<ArrayList<TedNews>>() {
            @Override
            public void call(final Subscriber<? super ArrayList<TedNews>> subscriber) {
                observable.subscribe(new Action1<Feed>() {
                    @Override
                    public void call(Feed feed) {
                        ArrayList<TedNews> out = new ArrayList<TedNews>();
                        int count = feed.getItemCount();
                        if (count > 0) {
                            for (int i = 0; i < count; i++) {
                                FeedItem tmpFeed = feed.getItem(i);
                                TedNews tmpNews = new TedNews();
                                tmpNews.setHeader(tmpFeed.getTitle());
                                tmpNews.setDate(tmpFeed.getPubDate().toString());
                                tmpNews.setDescription(tmpFeed.getDescriptionAsText());
                                tmpNews.setName(tmpFeed.getName());
                                out.add(tmpNews);
                            }
                            subscriber.onNext(out);
                        } else {
                            subscriber.onError(new NullPointerException("Null Feed"));
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        subscriber.onError(throwable);
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        subscriber.onCompleted();
                    }
                });
            }
        });
    }

    @Override
    public Observable<TedNews> getItem(String url) {
        return null;
    }

    private Feed getAll(String url) throws MalformedURLException, FeedIOException, UnsupportedFeedException, FeedXMLParseException {
        return FeedParser.parse(new URL(url));
    }
}
