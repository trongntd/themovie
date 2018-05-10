package com.ea.themovie.component;

import com.ea.themovie.fragment.ListMovieFragment;
import com.ea.themovie.module.NetModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetModule.class})
public interface MovieComponent {
    void inject(ListMovieFragment listMovieFragment);
}
