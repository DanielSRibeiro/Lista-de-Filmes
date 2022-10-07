package com.example.movies.data.network.model

import android.os.Parcelable
import com.example.movies.BuildConfig
import com.example.movies.domain.model.Movie
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class MovieResponseDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("vote_average") val note: Double,
    @SerializedName("release_date") val releaseData: Date,
    @SerializedName("adult") var adult: Boolean,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("video") val video: Boolean,
    @SerializedName("vote_count") val voteCount: Int
):Parcelable

fun MovieResponseDto.movieResponseDtoToMovie() : Movie {
    return Movie(
        id = this.id,
        title = this.title,
        posterPath = this.posterPath,
        overview = this.overview,
        note = this.note,
        releaseData = this.releaseData,
        genreIds = this.genreIds,
        backdropPath = this.backdropPath,
        posterUrl = BuildConfig.BASE_IMAGEM+this.posterPath,
        backdropUrl = BuildConfig.BASE_IMAGEM+this.backdropPath
    )
}