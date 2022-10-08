package com.example.core.domain.model

import java.util.*

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
)