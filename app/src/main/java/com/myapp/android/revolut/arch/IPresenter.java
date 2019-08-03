package com.myapp.android.revolut.arch;

public interface IPresenter<T extends IView> {

    void attachView(T t);

    void detachView();

}