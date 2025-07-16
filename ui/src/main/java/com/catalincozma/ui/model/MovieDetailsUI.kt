package com.catalincozma.ui.model

import com.catalincozma.model.Genre
import com.catalincozma.model.MovieDetails

data class MovieDetailsUI (
    val id: Int,
    val title: String,
    val tagline: String?,
    val overview: String,
    val releaseDate: String,
    val voteAverage: String,
    val voteCount: Int,
    val posterPath: String?,
    val backdropPath: String?,
    val genres: List<GenreUi>,
    val isFavorite: Boolean = false
)

data class GenreUi(
    val id: Int,
    val name: String
)

fun MovieDetails.toUi(): MovieDetailsUI {
    return MovieDetailsUI(
   id = id,
        title = title,
        tagline = tagline,
        overview = overview,
        releaseDate = releaseDate.take(4),
        voteAverage = String.format("%.1f", voteAverage),
        voteCount = voteCount,
        posterPath = posterPath,
        backdropPath = backdropPath,
        genres = genres.map { it.toUI()},
        isFavorite = isFavorite
    )
}

fun Genre.toUI() :GenreUi {
    return GenreUi(id = id, name = name)
}