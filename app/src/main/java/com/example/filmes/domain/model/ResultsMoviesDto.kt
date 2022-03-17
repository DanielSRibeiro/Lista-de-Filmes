package com.example.filmes.domain.model

import com.google.gson.annotations.SerializedName

data class ResultsMoviesDto(

    @SerializedName("page")
    var page: Int,

    @SerializedName("results")
    var movieList: ArrayList<MovieDto>,

    @SerializedName("total_pages")
    var totalPages: Int,

    @SerializedName("total_results")
    var totalResults: Int
)