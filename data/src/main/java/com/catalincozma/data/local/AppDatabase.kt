package com.catalincozma.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [FavoriteMovieEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteMoviesDao(): FavoriteMoviesDao
}
