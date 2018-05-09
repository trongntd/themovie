package com.ea.themovie.repository;


import com.ea.themovie.entity.Movie;
import com.ea.themovie.entity.MovieList;
import com.ea.themovie.entity.MovieVideo;

import java.util.List;

public interface MovieRepository {
    void getPopularMovies(int page, MovieRepositoryCallback<MovieList> callback);

    void getTopRatedMovies(int page, MovieRepositoryCallback<MovieList> callback);

    void getMovieVideo(String movieId, MovieRepositoryCallback<MovieVideo> callback);

    List<Movie> getFavoriteMovies();

    void addToFavorite(Movie movie);

    void removeFromFavorite(Movie movie);

    interface MovieRepositoryCallback<T> {
        void onSuccess(T t);

        void onError(int error, String message);
    }
}
