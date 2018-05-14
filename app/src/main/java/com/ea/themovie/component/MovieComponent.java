package com.ea.themovie.component;

import com.ea.themovie.fragment.ListMovieFragment;
import com.ea.themovie.fragment.MovieDetailFragment;
import com.ea.themovie.fragment.MovieInfoFragment;
import com.ea.themovie.fragment.MovieReviewFragment;
import com.ea.themovie.module.AppModule;
import com.ea.themovie.module.NetModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface MovieComponent {
    void inject(ListMovieFragment listMovieFragment);
    void inject(MovieReviewFragment movieReviewFragment);
    void inject(MovieDetailFragment movieDetailFragment);
    void inject(MovieInfoFragment movieInfoFragment);
}
