package com.example.filmes.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class MovieDto(

    @SerializedName("poster_path")
    val posterFilme: String,

    @SerializedName("title")
    val tituloFilme: String,

    @SerializedName("overview")
    val sinopse: String,

    @SerializedName("vote_average")
    val notaMedia: Double,

    @SerializedName("release_date")
    val dataLancamento: Date,

    @SerializedName("genre_ids")
    var generosIds: IntArray,

    @SerializedName("adult")
    var adult: Boolean,
    
    @SerializedName("backdrop_path")
    val backdropPath: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("original_language")
    val originalLanguage: String,

    @SerializedName("original_title")
    val originalTitle: String,

    @SerializedName("popularity")
    val popularity: Double,

    @SerializedName("video")
    val video: Boolean,

    @SerializedName("vote_count")
    val voteCount: Int
):Parcelable