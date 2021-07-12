package com.example.filmes.domain.model

import com.google.gson.annotations.SerializedName

data class ResultsCategoriesDto (

    @SerializedName("genres")
    var generosFilme : ArrayList<CategoriesDto>
)