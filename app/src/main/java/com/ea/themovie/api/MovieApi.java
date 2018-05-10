package com.ea.themovie.api;


import com.ea.themovie.entity.MovieList;
import com.ea.themovie.entity.MovieVideo;
import com.ea.themovie.util.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {
    @GET("3/movie/popular?api_key=" + Constants.API_KEY)
    Call<MovieList> getPopularMovies(@Query("page") int page);

    @GET("3/movie/top_rated?api_key=" + Constants.API_KEY)
    Call<MovieList> getTopRatedMovies(@Query("page") int page);

    @GET("3/movie/{movie_id}/videos?api_key=" + Constants.API_KEY)
    Call<MovieVideo> getMovieVideo(@Path("movie_id") String movieId);
}
