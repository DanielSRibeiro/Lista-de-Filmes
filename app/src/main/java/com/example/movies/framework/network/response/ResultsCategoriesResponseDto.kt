package com.example.movies.framework.network.response

import com.google.gson.annotations.SerializedName

data class ResultsCategoriesResponseDto (
    @SerializedName("genres") var data : ArrayList<CategoriesResponseDto>
)