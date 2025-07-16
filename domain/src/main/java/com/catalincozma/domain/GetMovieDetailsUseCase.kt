package com.catalincozma.domain

import com.catalincozma.model.MovieDetails
import kotlinx.coroutines.flow.Flow

interface GetMovieDetailsUseCase {
    suspend fun getMovieDetails(id: Int): Flow<Result<MovieDetails>>
}