package com.andresgarrido.kueskichallenge.data

import javax.inject.Inject

/**
 * @author Andres Garrido on July 31, 2024
 *         Copyright (C) 2024. All rights reserved.
 */
class MoviesRepository @Inject constructor(
    private val apiService: MovieApiService
){
    suspend fun getAllMovies(page: Int, language: String = "en-US") =
        apiService.getAllMovies(page, language)

    suspend fun getNowPlayingMovies(page: Int, language: String = "en-US") =
        apiService.getNowPlaying(page, language)
}
