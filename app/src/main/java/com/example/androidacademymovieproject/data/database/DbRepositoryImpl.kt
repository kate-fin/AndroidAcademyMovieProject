package com.example.androidacademymovieproject.data.database

import android.content.Context
import com.example.androidacademymovieproject.data.MovieRepository
import com.example.androidacademymovieproject.model.Actor
import com.example.androidacademymovieproject.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DbRepositoryImpl(val db: MovieDatabase) : DbRepository {

//    private val db = MovieDatabase.getInstance(appContext)

    override suspend fun loadMovies(): List<Movie> = withContext(Dispatchers.IO) {
        return@withContext db.movieDao().getAll().map { response ->
            Movie(
                id = response.movie.id,
                imageCardUrl = response.movie.imageCardUrl,
                name = response.movie.name,
                genres = Converters().fromStringToList(response.movie.genres),
                rating = response.movie.rating,
                countReviews = response.movie.countReviews,
                imageDetailsUrl = response.movie.imageDetailsUrl,
                duration = response.movie.duration,
                storyLine = response.movie.storyLine,
                actors = response.actors.map { actor ->
                    Actor(
                        id = actor.id,
                        name = actor.name,
                        imageUrl = actor.imageUrl
                    )
                },
                countStars = response.movie.countStars,
                isLiked = response.movie.isLiked
            )
        }
    }

    override suspend fun loadMovie(movieId: Int): Movie? = withContext(Dispatchers.IO) {
        return@withContext db.movieDao().getMovieById(movieId).let { response ->
            Movie(
                id = response.movie.id,
                imageCardUrl = response.movie.imageCardUrl,
                name = response.movie.name,
                genres = Converters().fromStringToList(response.movie.genres),
                rating = response.movie.rating,
                countReviews = response.movie.countReviews,
                imageDetailsUrl = response.movie.imageDetailsUrl,
                duration = response.movie.duration,
                storyLine = response.movie.storyLine,
                actors = response.actors.map { actor ->
                    Actor(
                        id = actor.id,
                        name = actor.name,
                        imageUrl = actor.imageUrl
                    )
                },
                countStars = response.movie.countStars,
                isLiked = response.movie.isLiked
            )
        }
    }

    override suspend fun insertMovies(movies: List<Movie>) = withContext(Dispatchers.IO) {
        db.movieDao().insert(movies.map { movie ->
            EntityMovie(
                id = movie.id,
                imageCardUrl = movie.imageCardUrl,
                name = movie.name,
                genres = Converters().fromListToString(movie.genres),
                rating = movie.rating,
                countReviews = movie.countReviews,
                imageDetailsUrl = movie.imageDetailsUrl,
                duration = movie.duration,
                storyLine = movie.storyLine,
                countStars = movie.countStars,
                isLiked = movie.isLiked
            )
        })
    }
}