package com.catalincozma.data

import com.catalincozma.data.remote.api.MovieApiService
import com.catalincozma.data.remote.dto.GenreDto
import com.catalincozma.data.remote.dto.MovieDetailsDto
import com.catalincozma.data.remote.dto.MovieDto
import com.catalincozma.data.remote.response.MoviesResponse
import com.catalincozma.data.repository.MoviesRepositoryImpl
import com.catalincozma.model.RecommendationType
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class MoviesRepositoryImplTest {

    private lateinit var api: MovieApiService
    private lateinit var repository: MoviesRepositoryImpl

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        api = mockk()
        repository = MoviesRepositoryImpl(api)
    }

    @Test
    fun `fetchMovies returns success when API call is successful`() = runTest(dispatcher) {
        // Given
        val recommendationType = RecommendationType.NOW_PLAYING
        val fakeResponse = MoviesResponse(
            results = listOf(
                MovieDto(id = 1, overview = "Test Movie", posterPath = null, voteAverage = 7.5, releaseDate = "")
            )
        )
        coEvery { api.getMovieByTag(any()) } returns Response.success(fakeResponse)

        // When
        val result = repository.fetchMovies(recommendationType)

        // Then
        assertTrue(result.isSuccess)
    }

    @Test
    fun `getMovieDetails returns movie when API call is successful`() = runTest(dispatcher) {
        // Given
        val movieId = 123
        val movieDetailsDto = mockMovieDetails()
        coEvery { api.getMovieDetails(movieId) } returns Response.success(movieDetailsDto)

        // When
        val result = repository.getMovieDetails(movieId)

        // Then
        assertTrue(result.isSuccess)
    }

    @Test
    fun `getMovieDetails returns failure when response is null`() = runTest(dispatcher) {
        // Given
        val movieId = 123
        coEvery { api.getMovieDetails(movieId) } returns Response.success(null)

        // When
        val result = repository.getMovieDetails(movieId)

        // Then
        assertTrue(result.isFailure)
    }

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
        genres: List<GenreDto> = listOf(mockGenre()),
    ) = MovieDetailsDto(
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
    )

    private fun mockGenre(id: Int = 1, name: String = "Action") = GenreDto(id, name)
}