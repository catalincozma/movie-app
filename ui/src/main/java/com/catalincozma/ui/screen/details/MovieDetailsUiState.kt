package com.catalincozma.ui.screen.details

import com.catalincozma.ui.model.MovieDetailsUI


sealed class MovieDetailsUiState {
    data object Loading : MovieDetailsUiState()

    data class MovieDetails(
        val movieDetails: MovieDetailsUI
    ) : MovieDetailsUiState()

    data class Error(val message: String) : MovieDetailsUiState()
}