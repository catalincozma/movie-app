package com.catalincozma.data.repository

import com.catalincozma.data.remote.api.MovieApiService
import com.catalincozma.data.remote.dto.asExternalModel
import com.catalincozma.domain.repo.MoviesRepository
import com.catalincozma.model.Movie
import com.catalincozma.model.MovieDetails
import com.catalincozma.model.RecommendationType
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val api: MovieApiService
) : MoviesRepository {

    override suspend fun fetchMovies(recommendationType: RecommendationType): Result<List<Movie>> {
        return try {
            val response = api.getMovieByTag(recommendationType.path)
            if (response.isSuccessful) {
                val movies = response.body()?.results?.map { it.asExternalModel() } ?: emptyList()
                Result.success(movies)
            } else {
                Result.failure(Exception("API error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMovieDetails(id: Int): Result<MovieDetails> {
        return try {
            val response = api.getMovieDetails(id)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body.asExternalModel())
                } else {
                    Result.failure(Exception("Empty response body"))
                }
            } else {
                Result.failure(Exception("API error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
