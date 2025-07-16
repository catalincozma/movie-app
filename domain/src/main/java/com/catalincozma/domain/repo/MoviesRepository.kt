package com.catalincozma.domain.repo

import com.catalincozma.model.Movie
import com.catalincozma.model.MovieDetails
import com.catalincozma.model.RecommendationType

interface MoviesRepository {
    suspend fun fetchMovies(recommendationType: RecommendationType):  Result<List<Movie>>
    suspend fun getMovieDetails(id: Int): Result<MovieDetails>
}