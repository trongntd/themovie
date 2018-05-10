package com.ea.themovie.mock;

import com.ea.themovie.MovieApplication;
import com.ea.themovie.component.DaggerMovieComponent;
import com.ea.themovie.module.MockAppModule;
import com.ea.themovie.module.MockNetModule;

public class MockMovieApplication extends MovieApplication {
    @Override
    protected void initialComponent() {
        mMovieComponent = DaggerMovieComponent.builder()
                .netModule(new MockNetModule())
                .appModule(new MockAppModule(this))
                .build();
    }
}
