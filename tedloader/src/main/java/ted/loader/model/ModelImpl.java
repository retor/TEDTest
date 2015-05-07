package ted.loader.model;


import com.parserlib.beans.Channel;
import com.parserlib.exceptions.LoaderException;
import com.parserlib.loader.RssLoader;
import com.parserlib.parser.Parser;
import com.parserlib.worker.IWorker;
import com.parserlib.worker.MainWorker;
import rx.Observable;
import rx.Subscriber;
import ted.loader.interfaces.IModel;

import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;



/**
 * Created by retor on 05.05.2015.
 */
public class ModelImpl implements IModel<Channel> {


    @Override
    public Observable<ArrayList<Channel>> getData(final String url) {
        return Observable.create(new Observable.OnSubscribe<ArrayList<Channel>>() {
            @Override
            public void call(final Subscriber<? super ArrayList<Channel>> subscriber) {
                ArrayList<Channel> out = new ArrayList<Channel>();
                try {
                    IWorker worker = new MainWorker(new RssLoader(), new Parser());
                    out.addAll(worker.getData(url));
                } catch (LoaderException e) {
                    subscriber.onError(e);
                } catch (ParserConfigurationException e) {
                    subscriber.onError(e);
                }
                subscriber.onNext(out);
            }
        });
    }

    @Override
    public Observable<Channel> getItem(String url) {
        return null;
    }
}
