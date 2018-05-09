package com.ea.themovie.module;

import com.ea.themovie.repository.MockMovieRepositoryImp;
import com.ea.themovie.repository.MovieRepository;
import com.ea.themovie.repository.MovieRepositoryImp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MockNetModule extends NetModule{

    @Override
    @Singleton
    @Provides
    MovieRepository providerMovieRepository(){
        return new MockMovieRepositoryImp();
    }
}
