package com.ea.themovie.repository;

import com.ea.themovie.api.Error;
import com.ea.themovie.api.MovieApi;
import com.ea.themovie.entity.MovieList;
import com.ea.themovie.entity.MovieVideo;
import com.ea.themovie.util.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRepositoryImp implements MovieRepository {
    private MovieApi movieApi;

    public MovieRepositoryImp() {
        createMovieApi();
    }

    private void createMovieApi() {
        Gson gson = new GsonBuilder()
                .setDateFormat(Constants.API_DATE_FORMAT)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        movieApi = retrofit.create(MovieApi.class);
    }

    @Override
    public void getPopularMovies(int page, final MovieRepositoryCallback<MovieList> callback) {
        movieApi.getPopularMovies(page).enqueue(new MobileApiCallback<>(callback));
    }

    @Override
    public void getTopRatedMovies(int page, final MovieRepositoryCallback<MovieList> callback) {
        movieApi.getTopRatedMovies(page).enqueue(new MobileApiCallback<>(callback));
    }

    @Override
    public void getMovieVideo(String movieId, final MovieRepositoryCallback<MovieVideo> callback) {
        movieApi.getMovieVideo(movieId).enqueue(new MobileApiCallback<>(callback));
    }

    class MobileApiCallback<T> implements Callback<T>{
        MovieRepositoryCallback<T> callback;
        MobileApiCallback( MovieRepositoryCallback<T> callback){
            this.callback = callback;
        }

        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            if (callback != null) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(response.code(), response.message());
                }
            }
        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            if (callback != null) {
                callback.onError(Error.UNKNOWN, null);
            }
        }
    }
}
