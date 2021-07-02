package com.example.androidacademymovieproject.features.movies_list

import androidx.lifecycle.*
import com.example.androidacademymovieproject.data.MovieRepository
import com.example.androidacademymovieproject.model.Movie
import kotlinx.coroutines.launch

class MoviesListViewModel(private val repository: MovieRepository): ViewModel() {
    init {
        loadMovies()
        println("I was here_load movies___________________________")
        println(repository.javaClass.fields)
    }
    private val _moviesListLiveData = MutableLiveData<List<Movie>>()
    val moviesListLiveData: LiveData<List<Movie>> get() = _moviesListLiveData


    fun loadMovies() {
        viewModelScope.launch {//ioScope
            _moviesListLiveData.postValue(repository.loadMovies())
        }
    }
}