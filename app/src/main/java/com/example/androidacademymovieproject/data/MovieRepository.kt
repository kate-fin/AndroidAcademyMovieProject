package com.example.androidacademymovieproject.data

import com.example.androidacademymovieproject.model.Movie

interface MovieRepository {
    suspend fun loadMovies(): List<Movie>
    suspend fun loadMovie(movieId: Int): Movie?
}