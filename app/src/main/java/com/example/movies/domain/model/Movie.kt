package com.example.movies.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Movie (
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