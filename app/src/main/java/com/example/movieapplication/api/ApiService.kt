package com.example.movieapplication.api

import com.example.movieapplication.model.response.MovieDetailResponse
import com.example.movieapplication.model.response.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiService {
    @GET("movie/now_playing")
    fun getMovieNowPlaying(

    ): Call<MovieResponse>

    @GET("movie/{movieId}")
    fun getMovieDetail(
        @Path("movieId") movieId: String
    ): Call<MovieDetailResponse>
}