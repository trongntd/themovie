package com.ea.themovie.module;

import android.content.SharedPreferences;

import com.ea.themovie.repository.MockMovieRepositoryImp;
import com.ea.themovie.repository.MovieRepository;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MockNetModule extends NetModule{

    @Override
    @Singleton
    @Provides
    MovieRepository providerMovieRepository(SharedPreferences sp, Gson gson, Retrofit retrofit){
        return new MockMovieRepositoryImp(sp, gson, retrofit);
    }
}
