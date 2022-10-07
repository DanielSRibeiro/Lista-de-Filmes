package com.example.movies.data.network.model

import com.google.gson.annotations.SerializedName

data class ResultsCategoriesResponseDto (
    @SerializedName("genres") var data : ArrayList<CategoriesResponseDto>
)