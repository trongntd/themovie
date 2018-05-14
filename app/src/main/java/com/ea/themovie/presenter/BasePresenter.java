package com.ea.themovie.presenter;

public class BasePresenter<V> {
    protected V view;

    public void attachView(V view){
        this.view = view;
    }

    public void detachView(){
        this.view = null;
    }
}
