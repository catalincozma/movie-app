package com.catalincozma.model

data class Movie(
    val id: Int,
    val posterUrl: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val isFavorite: Boolean = false
)
