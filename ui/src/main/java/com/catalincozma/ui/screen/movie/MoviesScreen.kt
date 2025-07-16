package com.catalincozma.ui.screen.movie

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.catalincozma.model.RecommendationType

@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    onMovieClick: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val selectedType = (state as? MoviesUiState.MoviesUi)?.selectedType ?: RecommendationType.POPULAR

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        RecommendationTypeTabs(
            selectedType = selectedType,
            onTypeSelected = { viewModel.onTypeSelected(it) }
        )

        when (val uiState = state) {
            is MoviesUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is MoviesUiState.Error -> {
                Text(
                    text = uiState.message,
                    color = Color.Gray,
                    modifier = Modifier.padding(16.dp)
                )
            }

            is MoviesUiState.MoviesUi -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.padding(8.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(uiState.movies) { movie ->
                        MovieCard(
                            movie = movie,
                            onClick = { onMovieClick(movie.id) },
                            onFavoriteToggle = { viewModel.toggleFavorite(movie.id) })
                    }
                }
            }
        }
    }
}
