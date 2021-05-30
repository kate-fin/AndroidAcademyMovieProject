package com.example.androidacademymovieproject.data

import android.content.Context
import com.example.androidacademymovieproject.model.Actor
import com.example.androidacademymovieproject.model.Genre
import com.example.androidacademymovieproject.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.*
import kotlinx.serialization.json.*

interface MovieRepository {
    suspend fun loadMovies(): List<Movie>
    suspend fun loadMovie(movieId: Int): Movie?
}

internal class JsonMovieRepository(private val context: Context) : MovieRepository {
    private val jsonFormat = Json { ignoreUnknownKeys = true }

    private var movies: List<Movie>? = null

    override suspend fun loadMovies(): List<Movie> = withContext(Dispatchers.IO) {
        val cachedMovies = movies
        if (cachedMovies != null) {
            cachedMovies
        } else {
            val moviesFromJson = loadMoviesFromJsonFile()
            movies = moviesFromJson
            moviesFromJson
        }
    }

    private suspend fun loadMoviesFromJsonFile(): List<Movie> {
        val genresMap = loadGenres()
        val actorsMap = loadActors()

        val data = readAssetFileToString("data.json")
        return parseMovies(data, genresMap, actorsMap)
    }


    private suspend fun loadGenres(): List<Genre> = withContext(Dispatchers.IO) {
        val data = readAssetFileToString("genres.json")
        val jsonGenres = jsonFormat.decodeFromString<List<JsonGenre>>(string = data)
        jsonGenres.map { jsonGenre -> Genre(id = jsonGenre.id, name = jsonGenre.name) }
    }

    private fun readAssetFileToString(fileName: String): String {
        val stream = context.assets.open(fileName)
        return stream.bufferedReader().readText()
    }

    private suspend fun loadActors(): List<Actor> = withContext(Dispatchers.IO) {
        val data = readAssetFileToString("people.json")
        val jsonActors = jsonFormat.decodeFromString<List<JsonActor>>(string = data)

        jsonActors.map { jsonActor ->
            Actor(
                id = jsonActor.id,
                name = jsonActor.name,
                imageUrl = jsonActor.profilePicture
            )
        }
    }

    private fun parseMovies(
        jsonString: String,
        genreData: List<Genre>,
        actors: List<Actor>
    ): List<Movie> {
        val genresMap = genreData.associateBy(Genre::id)
        val actorsMap = actors.associateBy(Actor::id)

        val jsonMovies = jsonFormat.decodeFromString<List<JsonMovie>>(string = jsonString)

        return jsonMovies.map { jsonMovie ->
            Movie(
                id = jsonMovie.id,
                name = jsonMovie.title,
                storyLine = jsonMovie.overview,
                imageCardUrl = jsonMovie.posterPicture,
                imageDetailsUrl = jsonMovie.backdropPicture,
                countStars = (jsonMovie.ratings / 2).toInt(),
                countReviews = jsonMovie.votesCount,
                rating = if (jsonMovie.adult) 16 else 13,
                duration = jsonMovie.runtime,
                genres = jsonMovie.genreIds.map { id ->
                    genresMap[id].orThrow { IllegalArgumentException("Genre not found") }
                },
                actors = jsonMovie.actors.map { id ->
                    actorsMap[id].orThrow { IllegalArgumentException("Actor not found") }
                },
                isLiked = false
            )
        }
    }

    override suspend fun loadMovie(movieId: Int): Movie? {
        val cachedMovies = movies ?: loadMovies()
        return cachedMovies.find { it.id == movieId }
    }

    private fun <T : Any> T?.orThrow(createThrowable: () -> Throwable): T {
        return this ?: throw createThrowable()
    }
}