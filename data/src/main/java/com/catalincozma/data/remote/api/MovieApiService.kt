package com.catalincozma.data.remote.api

import com.catalincozma.data.remote.dto.MovieDetailsDto
import com.catalincozma.data.remote.response.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApiService {
    @GET("movie/{type}")
    suspend fun getMovieByTag(
        @Path("type") type: String
    ): Response<MoviesResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
    ): Response<MovieDetailsDto>
}