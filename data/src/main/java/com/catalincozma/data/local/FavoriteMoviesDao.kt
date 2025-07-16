package com.catalincozma.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMoviesDao {
    @Query("SELECT movieId FROM favorite_movies")
    suspend fun getFavoriteMovieIds(): List<Int>

    @Query("SELECT movieId FROM favorite_movies")
    fun observeFavoriteMovieIds(): Flow<List<Int>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorite(favoriteMovieEntity: FavoriteMovieEntity)

    @Query("DELETE FROM favorite_movies WHERE movieId = :movieId")
    suspend fun removeFavorite(movieId: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_movies WHERE movieId = :movieId)")
    suspend fun isFavorite(movieId: Int): Boolean
}
