package com.catalincozma.ui.screen.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catalincozma.domain.GetMoviesUseCase
import com.catalincozma.domain.ToggleFavoriteUseCase
import com.catalincozma.model.RecommendationType
import com.catalincozma.ui.model.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val movieUseCase: GetMoviesUseCase,
    private val favoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val selectedType = MutableStateFlow(RecommendationType.POPULAR)

    val state: StateFlow<MoviesUiState> = selectedType
        .flatMapLatest { type ->
            movieUseCase.getMoviesFlow(type)
                .map { result ->
                    result.fold(
                        onSuccess = { movies ->
                            MoviesUiState.MoviesUi(
                                movies = movies.map { it.toUi() },
                                selectedType = type
                            )
                        },
                        onFailure = {
                            MoviesUiState.Error(it.message ?: "Unknown error")
                        }
                    )
                }
                .onStart { emit(MoviesUiState.Loading) }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MoviesUiState.Loading
        )

    fun onTypeSelected(type: RecommendationType) {
        selectedType.value = type
    }

    fun toggleFavorite(movieId: Int) {
        viewModelScope.launch {
            favoriteUseCase.toggleFavorite(movieId)
        }
    }
}
