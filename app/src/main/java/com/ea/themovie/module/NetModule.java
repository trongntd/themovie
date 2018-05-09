package com.ea.themovie.module;

import android.content.SharedPreferences;

import com.ea.themovie.repository.MovieRepository;
import com.ea.themovie.repository.MovieRepositoryImp;
import com.ea.themovie.util.Constants;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {

    @Singleton
    @Provides
    MovieRepository providerMovieRepository(SharedPreferences sp, Gson gson, Retrofit retrofit){
        return new MovieRepositoryImp(sp, gson, retrofit);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gsonBuilder.setDateFormat(Constants.API_DATE_FORMAT);
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(Constants.API_BASE_URL)
                .build();
    }
}
