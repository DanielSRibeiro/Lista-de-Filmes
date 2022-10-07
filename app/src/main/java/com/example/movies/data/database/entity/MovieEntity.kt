package com.example.movies.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movies.domain.model.Movie
import com.example.movies.utilis.JsonService
import java.util.*

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey(autoGenerate = false) val id: Long,
    @ColumnInfo val genreIds: String,
    @ColumnInfo val posterPath: String,
    @ColumnInfo val title: String,
    @ColumnInfo val overview: String,
    @ColumnInfo val notaMedia: Double,
    @ColumnInfo val releaseData: String,
    @ColumnInfo val backdropPath: String,
)

fun MovieEntity.movieEntityToMovie() : Movie {
    return Movie(
        id = this.id.toInt(),
        posterPath = this.posterPath,
        title = this.title,
        overview = this.overview,
        note = this.notaMedia,
        backdropPath = this.backdropPath,
        genreIds = JsonService.fromIntArray(this.genreIds),
        releaseData = Date().toString()
    )
}