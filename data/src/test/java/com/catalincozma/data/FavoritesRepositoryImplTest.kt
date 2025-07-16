package com.catalincozma.data

import com.catalincozma.data.local.FavoriteMovieEntity
import com.catalincozma.data.local.FavoriteMoviesDao
import com.catalincozma.data.repository.FavoritesRepositoryImpl
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FavoritesRepositoryImplTest {

    private lateinit var dao: FavoriteMoviesDao
    private lateinit var repository: FavoritesRepositoryImpl
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        dao = mockk()
        repository = FavoritesRepositoryImpl(dao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getFavoriteMovieIds returns success`() = runTest {
        // Given
        val ids = listOf(1, 2, 3)
        coEvery { dao.getFavoriteMovieIds() } returns ids

        //When
        val result = repository.getFavoriteMovieIds()

        //then
        assertTrue(result.isSuccess)
        assertEquals(ids, result.getOrNull())
    }

    @Test
    fun `getFavoriteMovieIds returns failure`() = runTest {
        // given
        val exception = RuntimeException("DB error")
        coEvery { dao.getFavoriteMovieIds() } throws exception

        // when
        val result = repository.getFavoriteMovieIds()

        // then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `addFavorite adds movie and returns success`() = runTest {
        // given
        val movieId = 10
        coEvery { dao.addFavorite(FavoriteMovieEntity(movieId)) } just Runs

        // when
        val result = repository.addFavorite(movieId)

        // then
        assertTrue(result.isSuccess)
    }

    @Test
    fun `removeFavorite removes movie and returns success`() = runTest {
        //given
        val movieId = 7
        coEvery { dao.removeFavorite(movieId) } just Runs

        // when
        val result = repository.removeFavorite(movieId)

        // then
        assertTrue(result.isSuccess)
    }

    @Test
    fun `observeFavoriteMovieIds emits expected flow`() = runTest {
        // given
        every { dao.observeFavoriteMovieIds() } returns  flowOf(listOf(1, 2, 3))

        // when
        val result = repository.observeFavoriteMovieIds().first()

        // then
        assertEquals(listOf(1, 2, 3).toSet(), result)
    }

    @Test
    fun `observeFavoriteMovieIds emits emptySet on exception`() = runTest {
        every { dao.observeFavoriteMovieIds() } returns flow {
            throw RuntimeException("fail")
        }

        val result = repository.observeFavoriteMovieIds().first()

        assertEquals(emptySet<Int>(), result)
    }
}
