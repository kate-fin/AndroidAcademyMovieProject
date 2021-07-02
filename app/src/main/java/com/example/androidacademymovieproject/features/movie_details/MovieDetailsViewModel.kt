package com.example.androidacademymovieproject.features.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidacademymovieproject.data.MovieRepository
import com.example.androidacademymovieproject.model.Movie
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val repository: MovieRepository):ViewModel() {
    private val _movieLiveData = MutableLiveData<Movie>()
    val movieLiveData: LiveData<Movie> get() = _movieLiveData

    fun loadMovie(id: Int) {
        viewModelScope.launch {
            _movieLiveData.postValue(repository.loadMovie(id))
        }
    }

}