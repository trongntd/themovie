package com.ea.themovie.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.ea.themovie.util.Common;

import dagger.Provides;

public class MockAppModule extends AppModule{
    public MockAppModule(Application application) {
        super(application);
    }

    @Provides
    SharedPreferences provideSharePreferences(){
        return mApplication.getSharedPreferences(Common.SP_NAME_ANDROID_TEST, Context.MODE_PRIVATE);
    }
}
