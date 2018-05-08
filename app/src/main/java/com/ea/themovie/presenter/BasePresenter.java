package com.ea.themovie.presenter;

public class BasePresenter<V> {
    protected V view;

    public void attachView(V viewAction){
        this.view = viewAction;
    }

    public void detachView(){
        this.view = null;
    }
}
