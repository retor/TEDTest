package ted.loader.model;

import rx.Observable;

/**
 * Created by retor on 05.05.2015.
 */
public interface IModel<T> {
    Observable<T> getData(String url);
}
