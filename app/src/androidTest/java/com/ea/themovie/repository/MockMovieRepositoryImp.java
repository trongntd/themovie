package com.ea.themovie.repository;

import android.content.SharedPreferences;

import com.ea.themovie.entity.MovieList;
import com.ea.themovie.util.Constants;
import com.ea.themovie.util.TestData;
import com.google.gson.Gson;

import retrofit2.Retrofit;

public class MockMovieRepositoryImp extends MovieRepositoryImp{
    public MockMovieRepositoryImp(SharedPreferences sharedPreferences, Gson gson, Retrofit retrofit) {
        super(sharedPreferences, gson, retrofit);
    }

    @Override
    public void getPopularMovies(int page, final MovieRepositoryCallback<MovieList> callback) {
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
        } else if (testId == TestData.TEST_DATA_ID_04) {
            sp.edit().remove(Constants.SP_FAVORITE_MOVIE).apply();
            if (callback != null) {
                String json = TestData.getMovieApiData(testId);
                MovieList movieList = gson.fromJson(json, MovieList.class);
                callback.onSuccess(movieList);
            }
        }  else if (testId == TestData.TEST_DATA_ID_05) {
            sp.edit().remove(Constants.SP_FAVORITE_MOVIE).apply();
            if (callback != null) {
                String json = TestData.getMovieApiData(TestData.TEST_DATA_ID_01);
                MovieList movieList = gson.fromJson(json, MovieList.class);
                callback.onSuccess(movieList);
            }
        } else {
            super.getPopularMovies(page, callback);
        }
    }

    @Override
    public void getTopRatedMovies(int page, MovieRepositoryCallback<MovieList> callback) {
        int testId = TestData.getTestId();
        if (testId == TestData.TEST_DATA_ID_03) {
            if (callback != null) {
                String json = TestData.getMovieApiData(testId);
                MovieList movieList = gson.fromJson(json, MovieList.class);
                callback.onSuccess(movieList);
            }
        }  else if (testId == TestData.TEST_DATA_ID_05) {
            sp.edit().remove(Constants.SP_FAVORITE_MOVIE).apply();
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
