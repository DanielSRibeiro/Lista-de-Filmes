package com.example.movies.data.network.model

import com.google.gson.annotations.SerializedName

data class ResultsMoviesDto(
    @SerializedName("results") var data: ArrayList<MovieDto>,
    @SerializedName("page") var page: Int,
    @SerializedName("total_pages") var totalPages: Int,
    @SerializedName("total_results") var totalResults: Int
)