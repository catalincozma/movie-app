package com.catalincozma.domain

import com.catalincozma.model.Movie
import com.catalincozma.model.RecommendationType
import kotlinx.coroutines.flow.Flow

interface GetMoviesUseCase {
    suspend fun getMoviesFlow(recommendationType: RecommendationType): Flow<Result<List<Movie>>>
}
