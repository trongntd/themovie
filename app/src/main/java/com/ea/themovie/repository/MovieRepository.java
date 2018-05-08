package com.ea.themovie.repository;


import com.ea.themovie.entity.MovieList;
import com.ea.themovie.entity.MovieVideo;

public interface MovieRepository {
    void getPopularMovies(int page, MovieRepositoryCallback<MovieList> callback);

    void getTopRatedMovies(int page, MovieRepositoryCallback<MovieList> callback);

    void getMovieVideo(String movieId, MovieRepositoryCallback<MovieVideo> callback);

    interface MovieRepositoryCallback<T> {
        void onSuccess(T t);

        void onError(int error, String message);
    }
}
