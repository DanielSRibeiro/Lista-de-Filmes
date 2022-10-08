package com.example.movies.framework.network.model

import com.google.gson.annotations.SerializedName

data class ResultsCategoriesResponseDto (
    @SerializedName("genres") var data : ArrayList<CategoriesResponseDto>
)