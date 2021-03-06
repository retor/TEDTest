package ted.loader.interfaces;

import java.util.ArrayList;

/**
 * Created by retor on 05.05.2015.
 */
public interface IView<T> {
    void update(ArrayList<T> data);
    void loadItem(T item);
    void onError(Throwable t);
}
