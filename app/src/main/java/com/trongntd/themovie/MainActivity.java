package com.trongntd.themovie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.trongntd.themovie.entity.Movie;
import com.trongntd.themovie.presenter.ListMoviePresenter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ListMoviePresenter.ViewAction{
    ListMoviePresenter listMoviePresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listMoviePresenter = new ListMoviePresenter();
        listMoviePresenter.attachView(this);

        listMoviePresenter.loadPopularMovies(1);
    }

    @Override
    protected void onDestroy() {
        listMoviePresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void showLoading(boolean show) {

    }

    @Override
    public void showListMovie(List<Movie> movies) {

    }

    @Override
    public void showError(String error) {

    }
}
