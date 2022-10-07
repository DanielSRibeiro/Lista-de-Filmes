package com.example.movies.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie (
    val id: Int,
    val title: String,
    val posterPath: String,
    val overview: String,
    val note: Double,
    val releaseData: String,
    val genreIds: List<Int>,
    val backdropPath: String,
): Parcelable