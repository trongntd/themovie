package com.ea.themovie.repository;

import android.util.Log;

import com.ea.themovie.api.Error;
import com.ea.themovie.api.MovieApi;
import com.ea.themovie.entity.MovieList;
import com.ea.themovie.entity.MovieVideo;
import com.ea.themovie.util.Constants;
import com.ea.themovie.util.TestData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MockMovieRepositoryImp extends MovieRepositoryImp{
    @Override
    public void getPopularMovies(int page, final MovieRepositoryCallback<MovieList> callback) {
        Gson gson = new GsonBuilder()
                .setDateFormat(Constants.API_DATE_FORMAT)
                .create();

        int testId = TestData.getTestId();
        if (testId == TestData.TEST_DATA_ID_01) {
            if (callback != null) {
                String json = TestData.getMovieApiData(testId);
                MovieList movieList = gson.fromJson(json, MovieList.class);
                callback.onSuccess(movieList);
            }
        } else if (testId == TestData.TEST_DATA_ID_02) {
            if (callback != null) {
                callback.onError(404, "");
            }
        } else {
            super.getPopularMovies(page, callback);
        }
    }

    @Override
    public void getTopRatedMovies(int page, MovieRepositoryCallback<MovieList> callback) {
        Gson gson = new GsonBuilder()
                .setDateFormat(Constants.API_DATE_FORMAT)
                .create();

        int testId = TestData.getTestId();
        if (testId == TestData.TEST_DATA_ID_03) {
            if (callback != null) {
                String json = TestData.getMovieApiData(testId);
                MovieList movieList = gson.fromJson(json, MovieList.class);
                callback.onSuccess(movieList);
            }
        } else {
            super.getTopRatedMovies(page, callback);
        }
    }
}
