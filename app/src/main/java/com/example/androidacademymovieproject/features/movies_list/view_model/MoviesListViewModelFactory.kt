package com.example.androidacademymovieproject.features.movies_list.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidacademymovieproject.data.MovieRepository

class MoviesListViewModelFactory(
    private val repository: MovieRepository
    ) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        MoviesListViewModel(repository) as T
}