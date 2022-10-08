package com.example.movies.framework.network.response

import com.google.gson.annotations.SerializedName

data class ResultsMoviesResponseDto(
    @SerializedName("results") var data: ArrayList<MovieResponseDto>,
    @SerializedName("page") var page: Int,
    @SerializedName("total_pages") var totalPages: Int,
    @SerializedName("total_results") var totalResults: Int
)