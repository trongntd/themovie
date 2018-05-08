package com.trongntd.themovie.presenter;

import com.trongntd.themovie.entity.Movie;
import com.trongntd.themovie.entity.MovieList;
import com.trongntd.themovie.repository.MovieRepository;

import java.util.List;

public class ListMoviePresenter extends BasePresenter<ListMoviePresenter.View> {
    private MovieRepository movieRepository;

    public ListMoviePresenter(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void loadPopularMovies(int page) {
        if (view != null) {
            view.showLoading(true);
        }

        movieRepository.getPopularMovies(page, new MovieRepository.MovieRepositoryCallback<MovieList>() {
            @Override
            public void onSuccess(MovieList movieList) {
                if (view != null) {
                    view.showLoading(false);
                    view.showListMovie(movieList.movies);
                }
            }

            @Override
            public void onError(int error, String message) {
                if (view != null) {
                    view.showLoading(false);
                    view.showError(message);
                }
            }
        });
    }

    public interface View {
        void showLoading(boolean show);

        void showListMovie(List<Movie> movies);

        void showError(String error);
    }
}
