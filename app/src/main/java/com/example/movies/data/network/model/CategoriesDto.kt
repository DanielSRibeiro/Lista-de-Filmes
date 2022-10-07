package com.example.movies.data.network.model

import com.example.movies.domain.model.Category
import com.google.gson.annotations.SerializedName

data class CategoriesDto (
    @SerializedName("id") var id :Int,
    @SerializedName("name") var name :String
)
fun CategoriesDto.categoriesDtoToCategory() : Category {
    return Category(
        id = this.id,
        name = this.name
    )
}