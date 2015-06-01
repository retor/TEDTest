package com.retor.tedtest.dataloader;

import rx.Observer;

/**
 * Created by Admin on 26.05.15.
 */
public interface IObservableModel<T> extends IModel{
    void setObserver(Observer<T> observer);
}
