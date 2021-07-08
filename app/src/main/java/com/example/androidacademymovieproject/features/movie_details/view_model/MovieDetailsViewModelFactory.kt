package com.example.androidacademymovieproject.features.movie_details.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidacademymovieproject.data.MovieRepository

class MovieDetailsViewModelFactory(private val repository: MovieRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        MovieDetailsViewModel(repository) as T
}