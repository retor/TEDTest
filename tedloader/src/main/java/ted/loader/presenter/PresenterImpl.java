package ted.loader.presenter;

import rx.functions.Action0;
import rx.functions.Action1;
import ted.loader.TedNews;
import ted.loader.interfaces.IModel;
import ted.loader.interfaces.IPresenter;
import ted.loader.interfaces.IScheduler;
import ted.loader.interfaces.IView;
import ted.loader.model.ModelImpl;

import java.util.ArrayList;


/**
 * Created by retor on 05.05.2015.
 */
public class PresenterImpl implements IPresenter {
    IModel<TedNews> model;
    IView<TedNews> view;
    ArrayList<TedNews> items = new ArrayList<TedNews>();
    IScheduler schedulers;


    public PresenterImpl(IView<TedNews> view) {
        this.model = new ModelImpl();
        this.view = view;
    }

    public PresenterImpl(IScheduler schedulers, IView<TedNews> view) {
        this.model = new ModelImpl();
        this.schedulers = schedulers;
        this.view = view;
    }

    @Override
    public void getData(final String url) {
        model.getData(url)
                .subscribeOn(schedulers.getBack())
                .observeOn(schedulers.getMain())
                .subscribe(new Action1<ArrayList<TedNews>>() {
                    @Override
                    public void call(ArrayList<TedNews> tedNewses) {
                        items.addAll(tedNewses);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view.onError(throwable);
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        view.update(items);
                    }
                });
    }

    @Override
    public void getItem(String url) {
        model.getItem(url);
    }
}

