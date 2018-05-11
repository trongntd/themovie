package com.trongntd.themovie.fragment;

public class PopularMovieFragment extends ListMovieFragment {
    @Override
    public void loadData() {
        listMoviePresenter.loadPopularMovies(1);
    }
}
