package com.ea.themovie.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ea.themovie.MainActivity;
import com.ea.themovie.R;
import com.ea.themovie.adapter.ListMovieAdapter;
import com.ea.themovie.entity.Movie;
import com.ea.themovie.presenter.ListMoviePresenter;
import com.ea.themovie.repository.MovieRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

abstract public class ListMovieFragment extends BaseFragment
        implements ListMoviePresenter.View, View.OnClickListener, ListMovieAdapter.OnItemMovieClick{
    {
        layoutResId = R.layout.fragment_list_movie;
    }

    abstract public void loadData();

    @Inject
    MovieRepository movieRepository;

    protected ListMovieAdapter adapter;
    protected RecyclerView rvListMovie;
    protected ProgressBar pbLoading;
    protected Button btnRetry;

    protected ListMoviePresenter listMoviePresenter;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rvListMovie = rootView.findViewById(R.id.rv_list_movie);
        pbLoading = rootView.findViewById(R.id.pb_loading_movie);
        btnRetry = rootView.findViewById(R.id.btn_retry);

        application.getMovieComponent().inject(this);

        btnRetry.setOnClickListener(this);

        listMoviePresenter = new ListMoviePresenter(movieRepository);
        listMoviePresenter.attachView(this);
        adapter = new ListMovieAdapter(activity, new ArrayList<Movie>());
        adapter.setListener(this);
        rvListMovie.setAdapter(adapter);
        rvListMovie.setLayoutManager(new GridLayoutManager(activity, 2));
        loadData();
    }

    @Override
    public void onFavoriteToggle(Movie movie) {
        listMoviePresenter.favoriteToggle(movie);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_retry) {
            loadData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listMoviePresenter.detachView();
    }

    @Override
    public void showLoading(boolean show) {
        pbLoading.setVisibility(show? View.VISIBLE : View.GONE);
    }

    @Override
    public void showListMovies(List<Movie> movies) {
        btnRetry.setVisibility(View.GONE);
        adapter.setData(movies);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(activity, error, Toast.LENGTH_SHORT).show();
        btnRetry.setVisibility(View.VISIBLE);

    }

    @Override
    public void refreshMoviesItem(Movie movie, boolean notifyNeighbor) {
        if (movie != null) {
            adapter.notifyDataItemChanged(movie);
            if (notifyNeighbor){
                ((MainActivity)activity).notifyDataNeighborTab(movie);
            }
        }
    }
}
