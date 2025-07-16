package com.catalincozma.ui.screen.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catalincozma.domain.GetMovieDetailsUseCase
import com.catalincozma.domain.ToggleFavoriteUseCase
import com.catalincozma.ui.model.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId: StateFlow<Int> = savedStateHandle.getStateFlow("movieId", 0)
        .filter { it != 0 }
        .stateIn(viewModelScope, SharingStarted.Eagerly, 0)

    private val retryTrigger = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    private val _errorEvents = MutableSharedFlow<String?>()
    val errorEvents: SharedFlow<String?> = _errorEvents


    val state: StateFlow<MovieDetailsUiState> = combine(
        movieId.filterNotNull(),
        retryTrigger.onStart { emit(Unit) }
    ) { id, _ -> id }
        .flatMapLatest { id ->
            getMovieDetailsUseCase.getMovieDetails(id)
                .map { result ->
                    result.fold(
                        onSuccess = { MovieDetailsUiState.MovieDetails(it.toUi()) },
                        onFailure = {
                            MovieDetailsUiState.Error(it.message ?: "Unknown error")
                        }
                    )
                }
                .onStart { emit(MovieDetailsUiState.Loading) }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MovieDetailsUiState.Loading
        )

    fun retry() {
        retryTrigger.tryEmit(Unit)
    }

    fun toggleFavorite() {
            viewModelScope.launch {
                val result = toggleFavoriteUseCase.toggleFavorite(movieId.value)
                result.onFailure {
                    _errorEvents.emit(it.message)
                }
            }
    }
}
