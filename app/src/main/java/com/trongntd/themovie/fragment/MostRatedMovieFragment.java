package com.trongntd.themovie.fragment;

public class MostRatedMovieFragment extends ListMovieFragment {
    @Override
    public void loadData() {
        listMoviePresenter.loadMostRatedMovies(1);
    }
}
