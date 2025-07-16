package com.catalincozma.domain.movies

import com.catalincozma.domain.repo.MoviesRepository
import com.catalincozma.domain.GetMovieDetailsUseCase
import com.catalincozma.domain.repo.FavoritesRepository
import com.catalincozma.model.MovieDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMovieDetailsUseCaseImpl @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val favoritesRepository: FavoritesRepository
) : GetMovieDetailsUseCase {
    override suspend fun getMovieDetails(id: Int): Flow<Result<MovieDetails>> {
        val movieDetailsFlow = flow {
            val result = moviesRepository.getMovieDetails(id)
            emit(result)
        }

        return movieDetailsFlow.combine(favoritesRepository.observeFavoriteMovieIds()) { detailsResult, favoriteIds ->
            detailsResult.map { movie ->
                movie.copy(isFavorite = movie.id in favoriteIds)
            }
        }
    }
}
