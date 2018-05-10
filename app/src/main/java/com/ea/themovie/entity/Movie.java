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

    public boolean isFavorite;

    @Override
    public boolean equals(Object obj) {
        return ((Movie) obj).id == this.id;
    }
}
