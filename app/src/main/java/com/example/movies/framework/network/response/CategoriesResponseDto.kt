package com.example.movies.framework.network.response

import com.example.core.domain.model.Category
import com.google.gson.annotations.SerializedName

data class CategoriesResponseDto (
    @SerializedName("id") var id :Int,
    @SerializedName("name") var name :String
)
fun CategoriesResponseDto.categoriesResponseDtoToCategory() : Category {
    return Category(
        id = this.id,
        name = this.name
    )
}