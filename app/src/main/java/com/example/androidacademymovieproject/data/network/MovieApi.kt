package com.example.androidacademymovieproject.data.network

import com.example.androidacademymovieproject.data.network.model.ResponseActor
import com.example.androidacademymovieproject.data.network.model.ResponseDetails
import com.example.androidacademymovieproject.data.network.model.ResponseMovie
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {
    @GET("3/movie/top_rated?api_key=88903affb25acfffe2947acd6bfb75c1&language=en-US&page=1&region=ru")
    suspend fun getMovies(): ResponseMovie

    @GET("3/movie/{movie_id}/credits?api_key=88903affb25acfffe2947acd6bfb75c1&language=en-US")
    suspend fun getActors(@Path("movie_id") movieId: Int): ResponseActor

    @GET("3/movie/{movie_id}?api_key=88903affb25acfffe2947acd6bfb75c1&language=en-US")
    suspend fun getDetails(@Path("movie_id") movieId: Int): ResponseDetails
}