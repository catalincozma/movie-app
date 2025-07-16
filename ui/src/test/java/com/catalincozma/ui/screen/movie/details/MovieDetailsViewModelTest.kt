package com.catalincozma.ui.screen.movie.details

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.catalincozma.domain.GetMovieDetailsUseCase
import com.catalincozma.domain.ToggleFavoriteUseCase
import com.catalincozma.model.Genre
import com.catalincozma.model.MovieDetails
import com.catalincozma.ui.MainDispatcherRule
import com.catalincozma.ui.screen.details.MovieDetailsUiState
import com.catalincozma.ui.screen.details.MovieDetailsViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase
    private lateinit var toggleFavoriteUseCase: ToggleFavoriteUseCase
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: MovieDetailsViewModel

    private val id = 42
    private val testMovieDetails = mockMovieDetails(id = id)
    private val testMovieDetailsResult = Result.success(testMovieDetails)

    @Before
    fun setup() {
        getMovieDetailsUseCase = mockk()
        toggleFavoriteUseCase = mockk()
        savedStateHandle = SavedStateHandle(mapOf("movieId" to id))
    }

    @Test
    fun `state emits MovieDetailsUiState MovieDetails on success`() = runTest {
        coEvery { getMovieDetailsUseCase.getMovieDetails(id) } returns flowOf(testMovieDetailsResult)
        coEvery { toggleFavoriteUseCase.toggleFavorite(id) } returns Result.success(Unit)

        viewModel = MovieDetailsViewModel(getMovieDetailsUseCase, toggleFavoriteUseCase, savedStateHandle)

        viewModel.state.test {
            val loading = awaitItem()
            assertTrue(loading is MovieDetailsUiState.Loading)

            val movieDetailsState = awaitItem()
            assertTrue(movieDetailsState is MovieDetailsUiState.MovieDetails)

            val movieUi = (movieDetailsState as MovieDetailsUiState.MovieDetails).movieDetails
            assertEquals(id, movieUi.id)
            assertEquals(testMovieDetails.title, movieUi.title)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `retry triggers reload`() = runTest {
        coEvery { getMovieDetailsUseCase.getMovieDetails(id) } returns flowOf(testMovieDetailsResult)

        viewModel = MovieDetailsViewModel(getMovieDetailsUseCase, toggleFavoriteUseCase, savedStateHandle)

        val job = launch {
            viewModel.state.collect { /* no-op */ }
        }

        viewModel.retry()

        advanceUntilIdle()

        coVerify(atLeast = 1) { getMovieDetailsUseCase.getMovieDetails(id) }

        job.cancel()
    }

    @Test
    fun `toggleFavorite calls use case and emits error on failure`() = runTest {
        val errorMsg = "Failed to toggle favorite"
        coEvery { toggleFavoriteUseCase.toggleFavorite(id) } returns Result.failure(Exception(errorMsg))
        coEvery { getMovieDetailsUseCase.getMovieDetails(id) } returns flowOf(testMovieDetailsResult)

        viewModel = MovieDetailsViewModel(getMovieDetailsUseCase, toggleFavoriteUseCase, savedStateHandle)

        val errors = mutableListOf<String?>()
        val job = launch {
            viewModel.errorEvents.collect {
                errors.add(it)
            }
        }

        viewModel.toggleFavorite()
        advanceUntilIdle()

        assertTrue(errors.contains(errorMsg))

        job.cancel()
    }

    private fun mockGenre(id: Int = 1, name: String = "Action") = Genre(id, name)

    private fun mockMovieDetails(
        id: Int = 1,
        title: String = "Mock Movie",
        tagline: String? = "Mock tagline",
        overview: String = "Mock overview",
        releaseDate: String = "2024-01-01",
        voteAverage: Double = 7.5,
        voteCount: Int = 100,
        posterPath: String? = "/poster.jpg",
        backdropPath: String? = "/backdrop.jpg",
        genres: List<Genre> = listOf(mockGenre()),
        isFavorite: Boolean = false
    ) = MovieDetails(
        id = id,
        title = title,
        tagline = tagline,
        overview = overview,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount,
        posterPath = posterPath,
        backdropPath = backdropPath,
        genres = genres,
        isFavorite = isFavorite
    )
}
