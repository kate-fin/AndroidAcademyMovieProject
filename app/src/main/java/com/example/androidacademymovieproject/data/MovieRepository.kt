package com.example.androidacademymovieproject.data

import android.content.Context
import com.example.androidacademymovieproject.model.Actor
import com.example.androidacademymovieproject.model.Genre
import com.example.androidacademymovieproject.model.Movie
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieRepository {
    suspend fun loadMovies(): List<Movie>
    suspend fun loadMovie(movieId: Int): Movie?
}

interface MovieApi {
    @GET("3/movie/top_rated?api_key=88903affb25acfffe2947acd6bfb75c1&language=en-US&page=1&region=ru")
    suspend fun getMovies(): ResponseMovie

    @GET("3/movie/{movie_id}/credits?api_key=88903affb25acfffe2947acd6bfb75c1&language=en-US")
    suspend fun getActors(@Path("movie_id") movieId: Int): ResponseActor

    @GET("3/movie/{movie_id}?api_key=88903affb25acfffe2947acd6bfb75c1&language=en-US")
    suspend fun getDetails(@Path("movie_id") movieId: Int): ResponseDetails
}

internal class JsonMovieRepository(private val context: Context) : MovieRepository {
    private val jsonFormat = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }
    private var movies: List<Movie>? = null
    private val baseUrl = "https://api.themoviedb.org/"
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(jsonFormat.asConverterFactory("application/json".toMediaType()))
        .build()
    val moviesApi = retrofit.create<MovieApi>()

    override suspend fun loadMovies(): List<Movie> = withContext(Dispatchers.IO) {
        println("Hi!_I'm loading for your movies")
        val cachedMovies = movies
        if (cachedMovies != null) {
            cachedMovies
        } else {
            val moviesFromJson = loadMoviesFromNet()//JsonFile()
            movies = moviesFromJson
            moviesFromJson
        }
    }

    private suspend fun loadMoviesFromNet(): List<Movie> {
        return parseMovies(moviesApi.getMovies().results)
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
                imageUrl = jsonActor.profilePicture ?: ""
            )
        }
    }

    private suspend fun parseMovies(
        moviesData: List<JsonMovie>
    ): List<Movie> {
        val picBaseUrl = "https://image.tmdb.org/t/p/"
        return moviesData.map { movie ->
            val movieDetails = moviesApi.getDetails(movie.id)
            val movieActors = moviesApi.getActors(movie.id).cast.subList(0, 10)
            Movie(
                id = movie.id,
                name = movie.title,
                storyLine = movie.overview,
                imageCardUrl = picBaseUrl + "w185" + movie.posterPicture,
                imageDetailsUrl = picBaseUrl + "w500" + movie.backdropPicture,
                countStars = (movie.ratings / 2).toInt(),
                countReviews = movie.votesCount,
                rating = if (movie.adult) 16 else 13,
                duration = movieDetails.runtime ?: 0,//13,//jsonMovie.runtime,
                genres = movieDetails.genres.map {
                    Genre(
                        id = it.id,
                        name = it.name
                    )
                },
                actors = movieActors.map { jsonActor ->
                    Actor(
                        id = jsonActor.id,
                        name = jsonActor.name,
                        imageUrl = picBaseUrl + "w92" + jsonActor.profilePicture ?: ""
                    )
                },
                isLiked = when ((0..2).random()) {
                    0 -> false
                    else -> true
                }
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