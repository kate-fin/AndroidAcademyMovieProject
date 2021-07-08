package com.example.androidacademymovieproject.data

import com.example.androidacademymovieproject.data.database.DbRepository
import com.example.androidacademymovieproject.data.network.NetworkRepository
import com.example.androidacademymovieproject.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepositoryImpl(
    val localData: DbRepository,
                          val remoteData:NetworkRepository):MovieRepository {


    override suspend fun loadMovies(): List<Movie> {
        return withContext(Dispatchers.IO){
            var data = localData.loadMovies()
            if (data.isEmpty()){
                data = remoteData.loadMovies()
                localData.insertMovies(data)
            }
            data
//            remoteData.loadMovies()
        }
    }

    override suspend fun loadMovie(movieId: Int): Movie? {
        return withContext(Dispatchers.IO){
            remoteData.loadMovie(movieId)
        }
    }
}