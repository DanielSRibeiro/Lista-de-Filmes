package com.example.filmes.domain.model

import com.google.gson.annotations.SerializedName

data class ResultsMoviesDto(

    @SerializedName("results")
    var movieList: ArrayList<MovieDto>,

    @SerializedName("total_pages")
    var totalPages: Integer,

    @SerializedName("total_results")
    var totalResults: Integer
)