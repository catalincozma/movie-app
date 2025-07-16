package com.catalincozma.domain.favorite

import com.catalincozma.domain.repo.FavoritesRepository
import com.catalincozma.domain.ToggleFavoriteUseCase
import javax.inject.Inject

class ToggleFavoriteUseCaseImpl @Inject constructor(private val favoritesRepository: FavoritesRepository) :
    ToggleFavoriteUseCase {
    override suspend fun toggleFavorite(movieId: Int): Result<Unit> {
        return try {
            val favoritesResult = favoritesRepository.getFavoriteMovieIds()
            if (favoritesResult.isFailure) {
                return Result.failure(
                    favoritesResult.exceptionOrNull() ?: Exception("Unknown error")
                )
            }
            val currentIds = favoritesResult.getOrThrow()
            if (movieId in currentIds) {
                favoritesRepository.removeFavorite(movieId)
            } else {
                favoritesRepository.addFavorite(movieId)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}