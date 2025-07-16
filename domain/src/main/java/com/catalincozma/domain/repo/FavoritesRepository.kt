package com.catalincozma.domain.repo

import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    suspend fun getFavoriteMovieIds(): Result<List<Int>>
    fun observeFavoriteMovieIds(): Flow<Set<Int>>
    suspend fun addFavorite(movieId: Int): Result<Unit>
    suspend fun removeFavorite(movieId: Int): Result<Unit>
    suspend fun isFavorite(movieId: Int): Result<Boolean>
}