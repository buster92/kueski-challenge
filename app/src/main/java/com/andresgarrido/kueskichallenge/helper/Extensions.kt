package com.andresgarrido.kueskichallenge.helper

import com.andresgarrido.kueskichallenge.data.model.Movie

fun Movie.getMidSizePosterUrl() = "https://image.tmdb.org/t/p/w500$posterPath"