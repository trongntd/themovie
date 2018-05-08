package com.trongntd.themovie.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.trongntd.themovie.R;
import com.trongntd.themovie.adapter.ListMovieAdapter;
import com.trongntd.themovie.entity.Movie;
import com.trongntd.themovie.presenter.ListMoviePresenter;
import com.trongntd.themovie.repository.MovieRepositoryImp;

import java.util.ArrayList;
import java.util.List;

abstract public class ListMovieFragment extends BaseFragment implements ListMoviePresenter.View{
    {
        layoutResId = R.layout.fragment_list_movie;
    }

    abstract public void loadData();

    protected ListMovieAdapter adapter;
    protected RecyclerView rvListMovie;
    protected ProgressBar pbLoading;
    protected ListMoviePresenter listMoviePresenter;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rvListMovie = rootView.findViewById(R.id.rv_list_movie);
        pbLoading = rootView.findViewById(R.id.pb_loading_movie);

        listMoviePresenter = new ListMoviePresenter(new MovieRepositoryImp());
        listMoviePresenter.attachView(this);
        Log.e("adad", "" + (rvListMovie == null));
        adapter = new ListMovieAdapter(activity, new ArrayList<Movie>());
        rvListMovie.setAdapter(adapter);
        rvListMovie.setLayoutManager(new GridLayoutManager(activity, 2));
        loadData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listMoviePresenter.detachView();
    }

    @Override
    public void showLoading(boolean show) {
        pbLoading.setVisibility(show? View.VISIBLE : View.GONE);
//        pbLoading.setVisibility(View.VISIBLE) ;
    }

    @Override
    public void showListMovie(List<Movie> movies) {
        adapter.addData(movies);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(activity, error, Toast.LENGTH_SHORT).show();
    }
}
