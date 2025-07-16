package com.catalincozma.domain.movies

import com.catalincozma.domain.repo.FavoritesRepository
import com.catalincozma.domain.repo.MoviesRepository
import com.catalincozma.domain.GetMoviesUseCase
import com.catalincozma.model.Movie
import com.catalincozma.model.RecommendationType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMoviesUseCaseImpl @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val favoritesRepository: FavoritesRepository
) : GetMoviesUseCase {

    override suspend fun getMoviesFlow(recommendationType: RecommendationType): Flow<Result<List<Movie>>> {
        val moviesFlow = flow {
            val result = moviesRepository.fetchMovies(recommendationType)
            emit(result)
        }

        return moviesFlow.combine(favoritesRepository.observeFavoriteMovieIds()) { moviesResult, favoriteIds ->
            moviesResult.map { movies ->
                movies.map { it.copy(isFavorite = it.id in favoriteIds) }
            }
        }
    }
}