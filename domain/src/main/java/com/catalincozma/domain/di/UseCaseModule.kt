package com.catalincozma.domain.di

import com.catalincozma.domain.GetMovieDetailsUseCase
import com.catalincozma.domain.GetMoviesUseCase
import com.catalincozma.domain.ToggleFavoriteUseCase
import com.catalincozma.domain.favorite.ToggleFavoriteUseCaseImpl
import com.catalincozma.domain.movies.GetMovieDetailsUseCaseImpl
import com.catalincozma.domain.movies.GetMoviesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindMovieUseCase(
        impl: GetMoviesUseCaseImpl
    ): GetMoviesUseCase

    @Binds
    abstract fun bindFavoriteUseCase(
        impl: ToggleFavoriteUseCaseImpl
    ): ToggleFavoriteUseCase

    @Binds
    abstract fun bindMovieDetails(
        impl: GetMovieDetailsUseCaseImpl
    ) : GetMovieDetailsUseCase
}
