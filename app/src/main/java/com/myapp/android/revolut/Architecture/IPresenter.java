package com.myapp.android.revolut.Architecture;

public interface IPresenter<T extends IView> {

    void attachView(T t);

    void detachView();

}