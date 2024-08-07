package com.andresgarrido.kueskichallenge.data.model

import com.google.gson.annotations.SerializedName

/**
 * @author Andres Garrido on July 31, 2024
 *         Copyright (C) 2024. All rights reserved.
 */
 data class Result<T>(
    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val results: List<T>,

    @SerializedName("total_pages")
    val totalPages: Long,

    @SerializedName("total_results")
    val totalResults: Long)
