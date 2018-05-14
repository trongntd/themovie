package com.ea.themovie.repository;


import com.ea.themovie.entity.Movie;
import com.ea.themovie.entity.MovieList;
import com.ea.themovie.entity.MovieReviews;
import com.ea.themovie.entity.MovieVideo;

import java.util.List;

public interface MovieRepository {
    void getPopularMovies(int page, MovieRepositoryCallback<MovieList> callback);

    void getTopRatedMovies(int page, MovieRepositoryCallback<MovieList> callback);

    void getMovieVideos(String movieId, MovieRepositoryCallback<MovieVideo> callback);

    void getMovieReviews(String movieId, MovieRepositoryCallback<MovieReviews> callback);

    List<Movie> getFavoriteMovies();

    void addToFavorite(Movie movie);

    void removeFromFavorite(Movie movie);

    interface MovieRepositoryCallback<T> {
        void onSuccess(T t);

        void onError(int error, String message);
    }
}
