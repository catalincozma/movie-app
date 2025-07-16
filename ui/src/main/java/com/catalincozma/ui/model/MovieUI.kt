package com.catalincozma.ui.model

import com.catalincozma.model.Movie

data class MovieUi (
    val id: Int,
    val posterUrl: String?,
    val releaseDate: String,
    val voteAverage: String,
    val isFavorite: Boolean = false
)

fun Movie.toUi(): MovieUi {
    return MovieUi(
        id = id,
        releaseDate = releaseDate,
        posterUrl = posterUrl,
        voteAverage = String.format("%.1f", voteAverage),
        isFavorite = isFavorite
    )
}
