package ted.loader.interfaces;

import rx.Observable;

import java.util.ArrayList;

/**
 * Created by retor on 05.05.2015.
 */
public interface IModel<T> {
    Observable<ArrayList<T>> getData(String url);
    Observable<T> getItem(String url);
}
