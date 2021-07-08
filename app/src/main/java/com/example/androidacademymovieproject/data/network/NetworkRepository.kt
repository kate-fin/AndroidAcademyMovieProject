package com.example.androidacademymovieproject.data.network

import com.example.androidacademymovieproject.model.Movie

interface NetworkRepository {
    suspend fun loadMovies(): List<Movie>
    suspend fun loadMovie(movieId: Int): Movie?
}