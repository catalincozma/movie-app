package com.catalincozma.data.remote.dto

import com.catalincozma.model.Genre
import com.catalincozma.model.MovieDetails
import com.google.gson.annotations.SerializedName

data class MovieDetailsDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("tagline") val tagline: String?,
    @SerializedName("overview") val overview: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("genres") val genres: List<GenreDto>
)

data class GenreDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)

fun MovieDetailsDto.asExternalModel(): MovieDetails {
    return MovieDetails(
        id = id,
        title = title,
        tagline = tagline,
        overview = overview,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount,
        posterPath = posterPath.let { "https://image.tmdb.org/t/p/w500$it" },
        backdropPath = backdropPath.let { "https://image.tmdb.org/t/p/w500$it" },
        genres = genres.map { Genre(it.id, it.name) },
    )
}

