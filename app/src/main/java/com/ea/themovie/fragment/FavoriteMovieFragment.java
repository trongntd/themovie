package com.ea.themovie.fragment;

import com.ea.themovie.MainActivity;
import com.ea.themovie.entity.Movie;

public class FavoriteMovieFragment extends ListMovieFragment {
    @Override
    public void loadData() {
        listMoviePresenter.loadFavoriteMovies();
    }

    @Override
    public void refreshMoviesItem(Movie movie, boolean notifyNeighbor) {
        loadData();
        if (notifyNeighbor) {
            ((MainActivity)activity).notifyDataNeighborTab(movie);
        }
    }
}
