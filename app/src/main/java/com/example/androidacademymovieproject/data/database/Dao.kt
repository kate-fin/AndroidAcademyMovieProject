package com.example.androidacademymovieproject.data.database

import androidx.room.*
import androidx.room.Dao
import com.example.androidacademymovieproject.model.Movie

@Dao
interface Dao {
    @Transaction
    @Query("SELECT * FROM movie" )
//            +
//            " WHERE movie.id == actor.movieId")
    fun getAll():List<MovieResponse>

    @Transaction
    @Query("SELECT * FROM movie WHERE movie.id == :id")
    fun getMovieById(id: Int): MovieResponse

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movies: List<EntityMovie>)
}