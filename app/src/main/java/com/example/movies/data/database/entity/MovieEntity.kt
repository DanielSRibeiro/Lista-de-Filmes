package com.example.movies.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey(autoGenerate = false) val id: Long,
    @ColumnInfo val generosIds:String,
    @ColumnInfo val posterFilme: String,
    @ColumnInfo val tituloFilme: String,
    @ColumnInfo val sinopse: String,
    @ColumnInfo val notaMedia: Double,
    @ColumnInfo val dataLancamento: String,
    @ColumnInfo var adult: Boolean,
    @ColumnInfo val backdropPath: String,
    @ColumnInfo val originalLanguage: String,
    @ColumnInfo val originalTitle: String,
    @ColumnInfo val popularity: Double,
    @ColumnInfo val video: Boolean,
    @ColumnInfo val voteCount: Int
)