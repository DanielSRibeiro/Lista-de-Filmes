package com.example.movies.framework.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movies.domain.model.Movie
import com.example.movies.util.Utils
import java.util.*

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey(autoGenerate = false) val id: Long,
    @ColumnInfo val genreIds: String,
    @ColumnInfo val posterPath: String?,
    @ColumnInfo val posterUrl: String?,
    @ColumnInfo val title: String,
    @ColumnInfo val overview: String,
    @ColumnInfo val notaMedia: Double,
    @ColumnInfo val releaseData: Date,
    @ColumnInfo val backdropPath: String?,
    @ColumnInfo val backdropUrl: String?,
)

fun MovieEntity.movieEntityToMovie() : Movie {
    return Movie(
        id = this.id.toInt(),
        posterPath = this.posterPath,
        posterUrl = this.posterUrl,
        title = this.title,
        overview = this.overview,
        note = this.notaMedia,
        backdropPath = this.backdropPath,
        backdropUrl = this.backdropUrl,
        genreIds = Utils.fromIntArray(this.genreIds),
        releaseData = this.releaseData
    )
}