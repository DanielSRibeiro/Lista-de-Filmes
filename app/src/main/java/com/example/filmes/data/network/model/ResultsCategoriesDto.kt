package com.example.filmes.data.network.model

import com.google.gson.annotations.SerializedName

data class ResultsCategoriesDto (
    @SerializedName("genres") var data : ArrayList<CategoriesDto>
)