package com.catalincozma.data.remote.dto

import com.catalincozma.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("id") val id: Int,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: Double
)

fun MovieDto.asExternalModel(): Movie {
    return Movie(
        id = id,
        posterUrl = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" },
        releaseDate = releaseDate,
        voteAverage = voteAverage
    )
}