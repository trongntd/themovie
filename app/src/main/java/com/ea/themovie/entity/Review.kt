package com.ea.themovie.entity

import com.google.gson.annotations.SerializedName

data class Review(
        @JvmField val id : String,
        @JvmField val author : String,
        @JvmField val url : String,
        @JvmField val content : String)

data class MovieReviews(
        @SerializedName("id")
        @JvmField val movieId: Int,
        @SerializedName("results")
        @JvmField var reviews: List<Review> )