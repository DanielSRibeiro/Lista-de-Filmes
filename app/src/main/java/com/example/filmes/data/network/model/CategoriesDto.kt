package com.example.filmes.data.network.model

import com.google.gson.annotations.SerializedName

data class CategoriesDto (
    @SerializedName("id") var id :Int,
    @SerializedName("name") var nome :String
)