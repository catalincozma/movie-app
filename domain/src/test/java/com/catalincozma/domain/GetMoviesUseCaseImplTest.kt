package com.catalincozma.domain

import com.catalincozma.domain.movies.GetMoviesUseCaseImpl
import com.catalincozma.domain.repo.FavoritesRepository
import com.catalincozma.domain.repo.MoviesRepository
import com.catalincozma.model.Movie
import com.catalincozma.model.RecommendationType
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test

class GetMoviesUseCaseImplTest {

    private lateinit var moviesRepository: MoviesRepository
    private lateinit var favoritesRepository: FavoritesRepository
    private lateinit var useCase: GetMoviesUseCaseImpl

    private val recommendationType = RecommendationType.NOW_PLAYING

    private val testMovies = listOf(
        Movie(id = 1, posterUrl = "", releaseDate = "", voteAverage = 7.0, isFavorite = false),
        Movie(id = 2, posterUrl = "", releaseDate = "", voteAverage = 6.5, isFavorite = false),
    )

    @Before
    fun setup() {
        moviesRepository = mockk()
        favoritesRepository = mockk()
        useCase = GetMoviesUseCaseImpl(moviesRepository, favoritesRepository)
    }

    @Test
    fun `returns movies with isFavorite true for favorite ids`() = runTest {
        // given
        coEvery { moviesRepository.fetchMovies(recommendationType) } returns Result.success(testMovies)
        every { favoritesRepository.observeFavoriteMovieIds() } returns flowOf(setOf(1))

        // when
        val result = useCase.getMoviesFlow(recommendationType).first()

        // then
        assertTrue(result.isSuccess)
        val movies = result.getOrThrow()
        assertEquals(2, movies.size)
        assertTrue(movies[0].isFavorite)  // id = 1
        assertFalse(movies[1].isFavorite) // id = 2
    }

    @Test
    fun `returns movies with isFavorite false when no favorites`() = runTest {
        // given
        coEvery { moviesRepository.fetchMovies(recommendationType) } returns Result.success(testMovies)
        every { favoritesRepository.observeFavoriteMovieIds() } returns flowOf(emptySet())

        // when
        val result = useCase.getMoviesFlow(recommendationType).first()

        // then
        assertTrue(result.isSuccess)
        val movies = result.getOrThrow()
        assertFalse(movies.any { it.isFavorite })
    }

    @Test
    fun `propagates failure from repository`() = runTest {
        // given
        val error = Exception("Network error")
        coEvery { moviesRepository.fetchMovies(recommendationType) } returns Result.failure(error)
        every { favoritesRepository.observeFavoriteMovieIds() } returns flowOf(setOf(1))

        // when
        val result = useCase.getMoviesFlow(recommendationType).first()

        // then
        assertTrue(result.isFailure)
        assertEquals(error, result.exceptionOrNull())
    }
}
