package com.trongntd.themovie.api;

import com.trongntd.themovie.entity.Movie;
import com.trongntd.themovie.entity.MovieList;
import com.trongntd.themovie.entity.MovieVideo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {
    String BASE_URL = "https://api.themoviedb.org/";
    String API_KEY = "af1153f5d5bee65b2d2a6d0c8aa9447f";

    @GET("3/movie/popular?api_key=af1153f5d5bee65b2d2a6d0c8aa9447f")
    Call<MovieList> getPopularMovies(@Query("page") int page);

    @GET("3/movie/top_rated?api_key=" + API_KEY)
    Call<MovieList> getTopRatedMovies(@Query("page") int page);

    @GET("3/movie/{movie_id}/videos?api_key=" + API_KEY)
    Call<MovieVideo> getMovieVideo(@Path("movie_id") String movieId);
}
