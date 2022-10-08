package com.example.movies.presentation.details

import android.os.Parcelable
import com.example.core.domain.model.Movie
import com.example.movies.BuildConfig
import com.example.movies.framework.network.response.movieResponseDtoToMovie
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class MovieArgs (
    val id: Int,
    val title: String,
    val posterPath: String?,
    val posterUrl: String?,
    val overview: String,
    val note: Double,
    val releaseData: Date,
    val genreIds: List<Int>,
    val backdropPath: String?,
    val backdropUrl: String?,
): Parcelable

fun MovieArgs.movieArgsToMovie() : Movie {
    return Movie(
        id = this.id,
        title = this.title,
        posterPath = this.posterPath,
        overview = this.overview,
        note = this.note,
        releaseData = this.releaseData,
        genreIds = this.genreIds,
        backdropPath = this.backdropPath,
        posterUrl = this.posterUrl,
        backdropUrl = this.backdropUrl
    )
}