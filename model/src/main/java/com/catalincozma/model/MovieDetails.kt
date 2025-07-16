package com.catalincozma.model

data class MovieDetails(
    val id: Int,
    val title: String,
    val tagline: String?,
    val overview: String,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val posterPath: String?,
    val backdropPath: String?,
    val genres: List<Genre>,
    val isFavorite: Boolean = false
)

data class Genre(
    val id: Int,
    val name: String
)