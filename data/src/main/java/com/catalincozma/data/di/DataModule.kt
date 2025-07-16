package com.catalincozma.data.di

import android.content.Context
import androidx.room.Room
import com.catalincozma.data.local.AppDatabase
import com.catalincozma.data.local.FavoriteMoviesDao
import com.catalincozma.domain.repo.FavoritesRepository
import com.catalincozma.data.repository.FavoritesRepositoryImpl
import com.catalincozma.domain.repo.MoviesRepository
import com.catalincozma.data.repository.MoviesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideMoviesRepository(
        impl: MoviesRepositoryImpl
    ): MoviesRepository = impl

    @Singleton
    @Provides
    fun provideFavoritesRepository(
        impl: FavoritesRepositoryImpl
    ): FavoritesRepository = impl

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "movies_db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideFavoriteMoviesDao(database: AppDatabase): FavoriteMoviesDao {
        return database.favoriteMoviesDao()
    }
}