package ted.loader.model;

import it.sauronsoftware.feed4j.FeedIOException;
import it.sauronsoftware.feed4j.FeedParser;
import it.sauronsoftware.feed4j.FeedXMLParseException;
import it.sauronsoftware.feed4j.UnsupportedFeedException;
import it.sauronsoftware.feed4j.bean.Feed;
import it.sauronsoftware.feed4j.bean.FeedItem;
import rx.Observable;
import rx.Subscriber;
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
        return Observable.create(new Observable.OnSubscribe<ArrayList<TedNews>>() {
            @Override
            public void call(final Subscriber<? super ArrayList<TedNews>> subscriber) {
                Feed f = null;
                try {
                    f = getAll(url);
                } catch (MalformedURLException e) {
                    subscriber.onError(e);
                } catch (FeedIOException e) {
                    subscriber.onError(e);
                } catch (UnsupportedFeedException e) {
                    subscriber.onError(e);
                } catch (FeedXMLParseException e) {
                    subscriber.onError(e);
                }
                ArrayList<TedNews> out = new ArrayList<TedNews>();
                int count;
                if (f!=null && (count = f.getItemCount()) > 0) {
                    for (int i = 0; i < count; i++) {
                        FeedItem tmpFeed = f.getItem(i);
                        TedNews tmpNews = new TedNews();
                        tmpNews.setHeader(tmpFeed.getTitle());
                        tmpNews.setDate(tmpFeed.getPubDate().toString());
                        tmpNews.setDescription(tmpFeed.getDescriptionAsText());
                        tmpNews.setName(tmpFeed.getName());
                        out.add(tmpNews);
                    }
                    subscriber.onNext(out);
                }
            }
        });
    }

    @Override
    public Observable<TedNews> getItem(String url) {
        return null;
    }

    private Feed getAll(String url) throws MalformedURLException, FeedIOException, UnsupportedFeedException, FeedXMLParseException {
        URL u = new URL(url);
        Feed f = FeedParser.parse(u);
        return f;
    }
}
