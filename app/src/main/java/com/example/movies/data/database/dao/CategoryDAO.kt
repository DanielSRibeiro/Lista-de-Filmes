package com.example.movies.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.data.database.entity.CategoryEntity

@Dao
interface CategoryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(categoryEntity: CategoryEntity): Long

    @Query("SELECT * FROM categoryentity")
    suspend fun getAll(): List<CategoryEntity>

}