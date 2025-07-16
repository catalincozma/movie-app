package com.catalincozma.ui.screen.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.catalincozma.ui.R

@Composable
fun MovieDetailsScreen(
    viewModel: MovieDetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.errorEvents.collect { message ->
            snackBarHostState.showSnackbar(message ?: "")
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { padding ->
        when (val uiState = state) {
            is MovieDetailsUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is MovieDetailsUiState.Error -> {
                AlertDialog(
                    onDismissRequest = {},
                    title = {
                        Text(text = stringResource(id = R.string.error_title))
                    },
                    text = {
                        Text(text = uiState.message)
                    },
                    confirmButton = {
                        Button(onClick = { viewModel.retry() }) {
                            Text(text = stringResource(id = R.string.retry))
                        }
                    }
                )
            }

            is MovieDetailsUiState.MovieDetails -> {
                MovieDetails(
                    movie = uiState.movieDetails,
                    onBackClick = onBackClick,
                    onFavoriteClick = { viewModel.toggleFavorite() },
                    paddingValues = padding
                )
            }
        }
    }
}