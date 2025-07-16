package com.catalincozma.domain

import com.catalincozma.domain.movies.GetMovieDetailsUseCaseImpl
import com.catalincozma.domain.repo.FavoritesRepository
import com.catalincozma.domain.repo.MoviesRepository
import com.catalincozma.model.Genre
import com.catalincozma.model.MovieDetails
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test

class GetMovieDetailsUseCaseImplTest {

    private lateinit var moviesRepository: MoviesRepository
    private lateinit var favoritesRepository: FavoritesRepository
    private lateinit var useCase: GetMovieDetailsUseCaseImpl

    private val testMovieId = 42
    private val baseMovieDetails = MovieDetails(
        id = testMovieId,
        title = "Test Movie",
        tagline = "Test Tagline",
        overview = "Test Overview",
        releaseDate = "2024-01-01",
        voteAverage = 8.5,
        voteCount = 1000,
        posterPath = "",
        backdropPath = "",
        genres = listOf(Genre(1, "Action")),
        isFavorite = false
    )

    @Before
    fun setup() {
        moviesRepository = mockk()
        favoritesRepository = mockk()
        useCase = GetMovieDetailsUseCaseImpl(moviesRepository, favoritesRepository)
    }

    @Test
    fun `returns MovieDetails with isFavorite true when id is in favorites`() = runTest {
        // given
        coEvery { moviesRepository.getMovieDetails(testMovieId) } returns Result.success(baseMovieDetails)
        every { favoritesRepository.observeFavoriteMovieIds() } returns flowOf(setOf(testMovieId))

        // when
        val result = useCase.getMovieDetails(testMovieId).first()

        // then
        assertTrue(result.isSuccess)
        val movieDetails = result.getOrThrow()
        assertEquals(true, movieDetails.isFavorite)
        assertEquals(baseMovieDetails.copy(isFavorite = true), movieDetails)
    }

    @Test
    fun `returns MovieDetails with isFavorite false when id is not in favorites`() = runTest {
        // given
        coEvery { moviesRepository.getMovieDetails(testMovieId) } returns Result.success(baseMovieDetails)
        every { favoritesRepository.observeFavoriteMovieIds() } returns flowOf(emptySet())

        // when
        val result = useCase.getMovieDetails(testMovieId).first()

        // then
        assertTrue(result.isSuccess)
        val movieDetails = result.getOrThrow()
        assertEquals(false, movieDetails.isFavorite)
        assertEquals(baseMovieDetails, movieDetails)
    }

    @Test
    fun `propagates failure from moviesRepository`() = runTest {
        // given
        val error = Exception("Movie not found")
        coEvery { moviesRepository.getMovieDetails(testMovieId) } returns Result.failure(error)
        every { favoritesRepository.observeFavoriteMovieIds() } returns flowOf(setOf(testMovieId))

        // when
        val result = useCase.getMovieDetails(testMovieId).first()

        // then
        assertTrue(result.isFailure)
        assertEquals(error, result.exceptionOrNull())
    }
}