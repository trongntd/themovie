package com.ea.themovie.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.ea.themovie.util.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    Application mApplication;

    public AppModule(Application application) {
        this.mApplication = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    SharedPreferences provideSharePreferences(){
        return mApplication.getSharedPreferences(Constants.SP_NAME, Context.MODE_PRIVATE);
    }
}
