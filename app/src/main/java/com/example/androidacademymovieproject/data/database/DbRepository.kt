package com.example.androidacademymovieproject.data.database

import com.example.androidacademymovieproject.model.Movie

interface DbRepository {
    suspend fun loadMovies(): List<Movie>
    suspend fun loadMovie(movieId: Int): Movie?
    suspend fun insertMovies(movies: List<Movie>)
}