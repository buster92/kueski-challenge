package com.andresgarrido.kueskichallenge.data

import com.andresgarrido.kueskichallenge.data.model.Movie
import com.andresgarrido.kueskichallenge.data.model.Result
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Andres Garrido on July 31, 2024
 *         Copyright (C) 2024. All rights reserved.
 */
interface MovieApiService {
    @GET("discover/movie")
    suspend fun getAllMovies(
        @Query("page") page: Int,
        @Query("language") language: String,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
    ): Result<Movie>

    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("page") page: Int,
        @Query("language") language: String,
        ): Result<Movie>
}