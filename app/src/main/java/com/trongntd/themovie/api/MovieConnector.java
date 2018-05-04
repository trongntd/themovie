package com.trongntd.themovie.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trongntd.themovie.entity.Movie;
import com.trongntd.themovie.entity.MovieList;
import com.trongntd.themovie.entity.MovieVideo;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieConnector {
    private static MovieConnector instance;

    public static MovieConnector getInstance(){
        if (instance == null) {
            instance = new MovieConnector();
        }

        return instance;
    }

    private MovieConnector(){
        createMovieApi();
    }

    private MovieApi movieApi;

    private void createMovieApi() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();

        Retrofit retrofit = new  Retrofit.Builder()
                .baseUrl(MovieApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        movieApi = retrofit.create(MovieApi.class);
    }

    public void getPopularMovies(int page, Callback<MovieList> callback){
        movieApi.getPopularMovies(page).enqueue(callback);
    }

    public void getTopRatedMovies(int page, Callback<MovieList> callback){
        movieApi.getTopRatedMovies(page).enqueue(callback);
    }

    public void getMovieVideo(String movieId, Callback<MovieVideo> callback){
        movieApi.getMovieVideo(movieId).enqueue(callback);
    }
}
