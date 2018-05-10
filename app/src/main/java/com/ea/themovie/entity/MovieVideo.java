package com.ea.themovie.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieVideo {

    @SerializedName("id")
    public String movieId;

    @SerializedName("results")
    public List<Video> videos;
}
