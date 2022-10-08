package com.example.movies.framework.db

import android.content.Context
import androidx.room.*
import com.example.movies.framework.db.converters.DateConverter
import com.example.movies.framework.db.dao.CategoryDAO
import com.example.movies.framework.db.dao.MovieDAO
import com.example.movies.framework.db.entity.CategoryEntity
import com.example.movies.framework.db.entity.MovieEntity

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