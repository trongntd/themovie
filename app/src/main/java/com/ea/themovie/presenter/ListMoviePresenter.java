package com.ea.themovie.presenter;


import com.ea.themovie.entity.Movie;
import com.ea.themovie.entity.MovieList;
import com.ea.themovie.repository.MovieRepository;

import java.util.List;

public class ListMoviePresenter extends BasePresenter<ListMoviePresenter.View> {
    private MovieRepository movieRepository;

    public ListMoviePresenter(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    private MovieRepository.MovieRepositoryCallback<MovieList> mGetMoviesCallback = new MovieRepository.MovieRepositoryCallback<MovieList>() {
        @Override
        public void onSuccess(MovieList movieList) {
            if (view != null) {
                view.showLoading(false);
                view.showListMovies(movieList.movies);
            }
        }

        @Override
        public void onError(int error, String message) {
            if (view != null) {
                view.showLoading(false);
                view.showError(message);
            }
        }
    };

    public void loadPopularMovies(int page) {
        if (view != null) {
            view.prepareLoading();
            view.showLoading(true);
        }

        movieRepository.getPopularMovies(page, mGetMoviesCallback);
    }

    public void loadMostRatedMovies(int page) {
        if (view != null) {
            view.prepareLoading();
            view.showLoading(true);
        }

        movieRepository.getTopRatedMovies(page, mGetMoviesCallback);
    }

    public void loadFavoriteMovies() {
        List<Movie> movies = movieRepository.getFavoriteMovies();
        if (view != null) {
            view.showListMovies(movies);
            view.showLoading(false);
        }
    }

    public void favoriteToggle(Movie movie) {
        if (movie.isFavorite) {
            movie.isFavorite = false;
            movieRepository.removeFromFavorite(movie);
        } else {
            movie.isFavorite = true;
            movieRepository.addToFavorite(movie);
        }
        if (view != null) {
            view.refreshMoviesItem(movie, true);
        }
    }

    public interface View {
        void prepareLoading();

        void showLoading(boolean show);

        void showListMovies(List<Movie> movies);

        void showError(String error);

        void refreshMoviesItem(Movie movie, boolean notifyNeighbor);
    }
}
