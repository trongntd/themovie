package com.ea.themovie;

import com.trongntd.themovie.api.MovieApi;
import com.trongntd.themovie.entity.Movie;
import com.trongntd.themovie.entity.MovieList;
import com.trongntd.themovie.presenter.ListMoviePresenter;
import com.trongntd.themovie.repository.MovieRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

public class ListMovieUnitTest {
    ListMoviePresenter presenter;
    ListMoviePresenter.View view;
    MovieRepository movieRepository;


    @Before
    public void setup() {
        movieRepository = Mockito.mock(MovieRepository.class);
        presenter = new ListMoviePresenter(movieRepository);
        view = Mockito.mock(ListMoviePresenter.View.class);
        presenter.attachView(view);
    }

    @Test
    public void loadListMovie_callShowLoading() {
        presenter.loadPopularMovies(1);
        Mockito.verify(view).showLoading(true);
    }

    @Test
    public void loadListMovie_callGetPopularMovies() {
        presenter.loadPopularMovies(1);
        Mockito.verify(movieRepository).getPopularMovies(Mockito.eq(1), Mockito.any(MovieRepository.MovieRepositoryCallback.class));
    }

    @Test
    public void loadListMovie_withRepositoryHavingMovies_callShowListMovie() {
        Movie movie = new Movie();
        final List<Movie> movies = new ArrayList<Movie>();
        movies.add(movie);

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                MovieList movieList = new MovieList();
                movieList.movies = movies;
                MovieRepository.MovieRepositoryCallback<MovieList> callback = invocation.getArgument(1);
                callback.onSuccess(movieList);
                return null;
            }
        })
                .when(movieRepository)
                .getPopularMovies(Mockito.eq(1), Mockito.any(MovieRepository.MovieRepositoryCallback.class));;

        presenter.loadPopularMovies(1);
        Mockito.verify(view).showListMovie(movies);
    }


    @Test
    public void loadListMovie_withRepositoryError_callShowError() {
        final String error = "Error";

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                MovieRepository.MovieRepositoryCallback<MovieList> callback = invocation.getArgument(1);
                callback.onError(Mockito.anyInt(), error);
                return null;
            }
        })
                .when(movieRepository)
                .getPopularMovies(Mockito.eq(1), Mockito.any(MovieRepository.MovieRepositoryCallback.class));;

        presenter.loadPopularMovies(1);
        Mockito.verify(view).showError(error);
    }


}
