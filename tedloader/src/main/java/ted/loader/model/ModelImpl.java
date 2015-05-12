package ted.loader.model;


import com.parserlib.beans.Channel;
import com.parserlib.exceptions.LoaderException;
import com.parserlib.exceptions.ParserException;
import com.parserlib.loader.RssLoader;
import com.parserlib.parser.Parser;
import com.parserlib.worker.IWorker;
import com.parserlib.worker.MainWorker;
import rx.Observable;
import rx.Subscriber;

import java.util.ArrayList;



/**
 * Created by retor on 05.05.2015.
 */
public class ModelImpl implements IModel<ArrayList<Channel>> {

    @Override
    public Observable<ArrayList<Channel>> getData(final String url) {
        return Observable.create(new Observable.OnSubscribe<ArrayList<Channel>>() {
            @Override
            public void call(final Subscriber<? super ArrayList<Channel>> subscriber) {
                try {
                    IWorker<ArrayList<Channel>> worker = new MainWorker(new RssLoader(), new Parser());
                    subscriber.onNext(worker.getData(url));
                } catch (LoaderException e) {
                    subscriber.onError(e);
                } catch (ParserException e) {
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        });
    }
}
