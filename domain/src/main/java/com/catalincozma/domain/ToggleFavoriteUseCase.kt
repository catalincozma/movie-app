package com.catalincozma.domain

interface ToggleFavoriteUseCase {
    suspend fun toggleFavorite(movieId: Int): Result<Unit>
}