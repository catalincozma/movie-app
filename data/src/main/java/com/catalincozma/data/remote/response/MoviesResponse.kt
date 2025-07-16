package com.catalincozma.data.remote.response

import com.catalincozma.data.remote.dto.MovieDto

data class MoviesResponse(
    val results: List<MovieDto>
)
