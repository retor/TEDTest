package ted.loader.presenter;


import com.parserlib.beans.Channel;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
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
    IModel<Channel> model;
    IView<Channel> view;
    ArrayList<Channel> items = new ArrayList<Channel>();
    IScheduler schedulers;

    public PresenterImpl(IView<Channel> view) {
        this.model = new ModelImpl();
        this.view = view;
        this.schedulers = new IScheduler() {
            @Override
            public Scheduler getMain() {
                return AndroidSchedulers.mainThread();
            }

            @Override
            public Scheduler getBack() {
                return Schedulers.io();
            }
        };
    }

    public PresenterImpl(IScheduler schedulers, IView<Channel> view) {
        this.model = new ModelImpl();
        this.schedulers = schedulers;
        this.view = view;
    }

    @Override
    public void getData(final String url) {
        model.getData(url)
                .subscribeOn(schedulers.getBack())
                .observeOn(schedulers.getMain())
                .subscribe(new Action1<ArrayList<Channel>>() {
                    @Override
                    public void call(ArrayList<Channel> tedNewses) {
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

