package com.example.movies.framework.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movies.framework.network.model.CategoriesResponseDto
import com.example.movies.domain.model.Category

@Entity
data class CategoryEntity(
    @PrimaryKey(autoGenerate = false) val id : Long,
    val name : String
)
fun CategoryEntity.categoryEntityToCategory() : Category {
    return Category(
        id = this.id.toInt(),
        name = this.name
    )
}