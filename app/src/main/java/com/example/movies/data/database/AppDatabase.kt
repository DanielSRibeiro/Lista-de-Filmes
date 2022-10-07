package com.example.movies.data.database

import android.content.Context
import androidx.room.*
import com.example.movies.data.database.converters.DateConverter
import com.example.movies.data.database.dao.MovieDao
import com.example.movies.data.database.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val movieDAO: MovieDao

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
                        "app_database"
                    ).build()
                }

                return instance
            }
        }
    }
}