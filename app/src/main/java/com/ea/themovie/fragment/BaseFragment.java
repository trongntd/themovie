package com.ea.themovie.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ea.themovie.MovieApplication;


public class BaseFragment extends Fragment{
    protected int layoutResId;

    protected View rootView;
    protected FragmentActivity activity;
    protected MovieApplication application;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (layoutResId > 0) {
            rootView = inflater.inflate(layoutResId, container, false);
        } else {
            rootView = super.onCreateView(inflater, container, savedInstanceState);
        }
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (FragmentActivity) context;
        application = (MovieApplication) activity.getApplication();
    }
}
