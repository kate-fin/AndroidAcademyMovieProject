package com.example.androidacademymovieproject

import com.example.androidacademymovieproject.data.MovieRepository

internal interface MovieRepositoryProvider {
    fun provideMovieRepository(): MovieRepository
}