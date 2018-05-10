package com.ea.themovie.repository;

import android.content.SharedPreferences;

import com.ea.themovie.api.Error;
import com.ea.themovie.api.MovieApi;
import com.ea.themovie.entity.Movie;
import com.ea.themovie.entity.MovieList;
import com.ea.themovie.entity.MovieVideo;
import com.ea.themovie.util.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MovieRepositoryImp implements MovieRepository {
    private MovieApi movieApi;
    protected SharedPreferences sp;
    protected Gson gson;
    private Retrofit retrofit;

    public MovieRepositoryImp(SharedPreferences sharedPreferences, Gson gson, Retrofit retrofit) {
        this.sp = sharedPreferences;
        this.gson = gson;
        this.retrofit = retrofit;
        createMovieApi();
    }

    private void createMovieApi() {
//        Gson gson = new GsonBuilder()
//                .setDateFormat(Constants.API_DATE_FORMAT)
//                .create();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constants.API_BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build();

        movieApi = retrofit.create(MovieApi.class);
    }

    @Override
    public void getPopularMovies(int page, final MovieRepositoryCallback<MovieList> callback) {
        movieApi.getPopularMovies(page).enqueue(new GetListMoviesCallback(callback));
    }

    @Override
    public void getTopRatedMovies(int page, final MovieRepositoryCallback<MovieList> callback) {
        movieApi.getTopRatedMovies(page).enqueue(new GetListMoviesCallback(callback));
    }

    @Override
    public void getMovieVideo(String movieId, final MovieRepositoryCallback<MovieVideo> callback) {
        movieApi.getMovieVideo(movieId).enqueue(new MobileApiCallback<>(callback));
    }

    @Override
    public List<Movie> getFavoriteMovies() {
        String str = sp.getString(Constants.SP_FAVORITE_MOVIE, null);
        if (str != null) {
            Type listType = new TypeToken<ArrayList<Movie>>(){}.getType();
            return gson.fromJson(str, listType);
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public void addToFavorite(Movie movie) {
        List<Movie> movies = getFavoriteMovies();
        movies.add(movie);
        saveFavoriteMovies(movies);
    }

    @Override
    public void removeFromFavorite(Movie movie) {
        List<Movie> movies = getFavoriteMovies();
        movies.remove(movie);
        saveFavoriteMovies(movies);
    }



    private void saveFavoriteMovies(List<Movie> movies) {
        String str = gson.toJson(movies);
        sp.edit().putString(Constants.SP_FAVORITE_MOVIE, str).apply();
    }

    private void markFavorite(List<Movie> movies) {
        List<Movie> favoriteMovies = getFavoriteMovies();
        for (Movie movie : movies) {
            movie.isFavorite = favoriteMovies.contains(movie);
        }
    }

    class GetListMoviesCallback extends MobileApiCallback<MovieList> {

        GetListMoviesCallback(MovieRepositoryCallback<MovieList> callback) {
            super(callback);
        }

        @Override
        public void onResponse(Call<MovieList> call, Response<MovieList> response) {
            if (callback != null) {
                if (response.isSuccessful()) {
                    MovieList movieList = response.body();
                    markFavorite(movieList.movies);
                    callback.onSuccess(movieList);
                } else {
                    callback.onError(response.code(), response.message());
                }
            }
        }
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
