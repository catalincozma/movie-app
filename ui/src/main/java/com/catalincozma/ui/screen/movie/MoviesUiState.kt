package com.catalincozma.ui.screen.movie

import com.catalincozma.model.RecommendationType
import com.catalincozma.ui.model.MovieUi

sealed class MoviesUiState {
    data object Loading : MoviesUiState()
    data class MoviesUi(
        val movies: List<MovieUi> = emptyList(),
        val selectedType: RecommendationType
    ) : MoviesUiState()
    data class Error(val message: String) : MoviesUiState()
}