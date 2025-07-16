package com.catalincozma.data.repository

import com.catalincozma.data.local.FavoriteMovieEntity
import com.catalincozma.data.local.FavoriteMoviesDao
import com.catalincozma.domain.repo.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(private val dao: FavoriteMoviesDao) :
    FavoritesRepository {

    override suspend fun getFavoriteMovieIds(): Result<List<Int>> = try {
        val list = dao.getFavoriteMovieIds()
        Result.success(list)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override fun observeFavoriteMovieIds(): Flow<Set<Int>> {
        return dao.observeFavoriteMovieIds()
            .map { it.toSet() }
            .catch { emit(emptySet()) }
    }

    override suspend fun addFavorite(movieId: Int): Result<Unit> = try {
        dao.addFavorite(FavoriteMovieEntity(movieId))
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun removeFavorite(movieId: Int): Result<Unit> = try {
        dao.removeFavorite(movieId)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun isFavorite(movieId: Int): Result<Boolean> = try {
        Result.success(dao.isFavorite(movieId))
    } catch (e: Exception) {
        Result.failure(e)
    }
}
