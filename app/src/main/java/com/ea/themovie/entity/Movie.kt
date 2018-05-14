package com.ea.themovie.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Movie(
        @JvmField val id: Int,
        @JvmField var title: String,
        @JvmField var overview: String,

        @SerializedName("poster_path")
        @JvmField var posterPath: String,

        @SerializedName("release_date")
        @JvmField var releaseDate: String,

        @SerializedName("vote_average")
        @JvmField var voteAverage: Double,

        @JvmField var isFavorite: Boolean = false ):Parcelable {

    constructor(id: Int, title: String, overview: String, posterPath: String, releaseDate: String, voteAverage: Double)
            : this(id, title, overview, posterPath, releaseDate, voteAverage, false)

    constructor() : this(0, "", "", "", "", 0.0)


    override fun equals(other: Any?): Boolean {
        return (other as Movie).id == this.id
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}