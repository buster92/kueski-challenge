package com.andresgarrido.kueskichallenge.data.model

import com.google.gson.annotations.SerializedName

/**
 * @author Andres Garrido on July 31, 2024
 *         Copyright (C) 2024. All rights reserved.
 */
data class Movie(
    @SerializedName("id")
    val id: Int,

    @SerializedName("original_title")
    val originalTitle: String,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("release_date")
    val releaseDate: String,

    @SerializedName("vote_average")
    val voteAverage: Float,

    @SerializedName("vote_count")
    val voteCount: Long,
)


