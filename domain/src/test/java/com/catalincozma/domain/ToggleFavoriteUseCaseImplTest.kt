package com.catalincozma.domain

import com.catalincozma.domain.favorite.ToggleFavoriteUseCaseImpl
import com.catalincozma.domain.repo.FavoritesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

class ToggleFavoriteUseCaseImplTest {

    private lateinit var favoritesRepository: FavoritesRepository
    private lateinit var useCase: ToggleFavoriteUseCase

    @Before
    fun setup() {
        favoritesRepository = mockk(relaxed = true)
        useCase = ToggleFavoriteUseCaseImpl(favoritesRepository)
    }

    @Test
    fun `removes favorite when movie is already in favorites`() = runTest {
        val movieId = 1
        coEvery { favoritesRepository.getFavoriteMovieIds() } returns Result.success(listOf(movieId))

        val result = useCase.toggleFavorite(movieId)

        coVerify { favoritesRepository.removeFavorite(movieId) }
        assertTrue(result.isSuccess)
    }

    @Test
    fun `adds favorite when movie is not in favorites`() = runTest {
        val movieId = 2
        coEvery { favoritesRepository.getFavoriteMovieIds() } returns Result.success(listOf())

        val result = useCase.toggleFavorite(movieId)

        coVerify { favoritesRepository.addFavorite(movieId) }
        assertTrue(result.isSuccess)
    }

    @Test
    fun `returns failure when getFavoriteMovieIds fails`() = runTest {
        val exception = Exception("Database error")
        coEvery { favoritesRepository.getFavoriteMovieIds() } returns Result.failure(exception)

        val result = useCase.toggleFavorite(3)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is Exception)
    }

    @Test
    fun `returns failure if addFavorite throws exception`() = runTest {
        val movieId = 4
        coEvery { favoritesRepository.getFavoriteMovieIds() } returns Result.success(listOf())
        coEvery { favoritesRepository.addFavorite(movieId) } throws RuntimeException("Unexpected error")

        val result = useCase.toggleFavorite(movieId)

        assertTrue(result.isFailure)
    }

    @Test
    fun `returns failure if removeFavorite throws exception`() = runTest {
        val movieId = 5
        coEvery { favoritesRepository.getFavoriteMovieIds() } returns Result.success(listOf(movieId))
        coEvery { favoritesRepository.removeFavorite(movieId) } throws RuntimeException("Unexpected error")

        val result = useCase.toggleFavorite(movieId)

        assertTrue(result.isFailure)
    }
}
