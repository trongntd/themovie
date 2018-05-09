package com.ea.themovie.mock;

import com.ea.themovie.MovieApplication;
import com.ea.themovie.component.DaggerMovieComponent;
import com.ea.themovie.component.MovieComponent;
import com.ea.themovie.module.MockNetModule;
import com.ea.themovie.module.NetModule;

public class MockMovieApplication extends MovieApplication {
    @Override
    protected void initialComponent() {
        mMovieComponent = DaggerMovieComponent.builder()
                .netModule(new MockNetModule())
                .build();
    }
}
