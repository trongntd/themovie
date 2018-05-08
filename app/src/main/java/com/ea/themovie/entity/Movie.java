package com.ea.themovie.entity;

import com.google.gson.annotations.SerializedName;

public class Movie {
    public int id;

    public String title;

    public String overview;

    @SerializedName("poster_path")
    public String posterPath;

    @SerializedName("release_date")
    public String releaseDate;

    @SerializedName("vote_average")
    public String voteAverage;

    public String isFavorited;
}
