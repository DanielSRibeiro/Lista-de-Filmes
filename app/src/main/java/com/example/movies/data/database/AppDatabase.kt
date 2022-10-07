package com.example.movies.data.database

import android.content.Context
import androidx.room.*
import com.example.movies.data.database.converters.DateConverter
import com.example.movies.data.database.dao.CategoryDAO
import com.example.movies.data.database.dao.MovieDAO
import com.example.movies.data.database.entity.CategoryEntity
import com.example.movies.data.database.entity.MovieEntity

const val NAME_DATA_BASE = "app_database"

@Database(
    entities = [MovieEntity::class, CategoryEntity::class],
    version = 1
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val movieDAO: MovieDAO
    abstract val categoryDAO: CategoryDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance: AppDatabase? = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        NAME_DATA_BASE
                    ).build()
                }

                return instance
            }
        }
    }
}