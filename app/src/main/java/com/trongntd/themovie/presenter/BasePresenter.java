package com.trongntd.themovie.presenter;

public class BasePresenter<V> {
    protected V viewAction;

    public void attachView(V viewAction){
        this.viewAction = viewAction;
    }

    public void detachView(){
        this.viewAction = null;
    }
}
