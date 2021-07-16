package com.example.filmes.data.local.entity

import android.provider.ContactsContract
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.filmes.domain.model.MovieDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.sql.Date
import java.text.DateFormat

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