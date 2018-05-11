package com.trongntd.themovie.repository;

import com.trongntd.themovie.entity.MovieList;
import com.trongntd.themovie.entity.MovieVideo;

import retrofit2.Callback;

public interface MovieRepository {
    void getPopularMovies(int page, MovieRepositoryCallback<MovieList> callback);

    void getTopRatedMovies(int page, MovieRepositoryCallback<MovieList> callback);

    void getMovieVideo(String movieId, MovieRepositoryCallback<MovieVideo> callback);

    interface MovieRepositoryCallback<T> {
        void onSuccess(T t);

        void onError(int error, String message);
    }
}
