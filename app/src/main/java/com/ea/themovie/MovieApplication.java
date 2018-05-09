package com.ea.themovie;

import android.app.Application;

import com.ea.themovie.component.DaggerMovieComponent;
import com.ea.themovie.component.MovieComponent;
import com.ea.themovie.module.AppModule;
import com.ea.themovie.module.NetModule;

public class MovieApplication extends Application {
    protected MovieComponent mMovieComponent;
    @Override
    public void onCreate() {
        super.onCreate();

        initialComponent();
    }

    protected void initialComponent() {
        mMovieComponent = DaggerMovieComponent.builder()
                .netModule(new NetModule())
                .appModule(new AppModule(this))
                .build();
    }

    public MovieComponent getMovieComponent() {
        return mMovieComponent;
    }
}
