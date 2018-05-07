package com.trongntd.themovie.presenter;

import android.util.Log;

import com.trongntd.themovie.api.MovieConnector;
import com.trongntd.themovie.entity.Movie;
import com.trongntd.themovie.entity.MovieList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListMoviePresenter extends BasePresenter<ListMoviePresenter.ViewAction>{

    public void loadPopularMovies(int page) {
        if (viewAction != null) {
            viewAction.showLoading(true);
        }
        MovieConnector.getInstance().getPopularMovies(page, new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                if (viewAction != null) {
                    viewAction.showLoading(false);
                }

                if (response.isSuccessful()) {
                    MovieList movieList = response.body();
                    if (viewAction != null) {
                        viewAction.showListMovie(movieList.movies);
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                if (viewAction != null) {
                    viewAction.showLoading(false);
                    viewAction.showError("Loading movie failed");
                }
            }
        });
    }

    public interface ViewAction{
        void showLoading(boolean show);
        void showListMovie(List<Movie> movies);
        void showError(String error);
    }
}
