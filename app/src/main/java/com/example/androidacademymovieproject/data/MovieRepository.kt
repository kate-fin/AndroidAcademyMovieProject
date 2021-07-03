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
import kotlin.random.Random

interface MovieRepository {
    suspend fun loadMovies(): List<Movie>
    suspend fun loadMovie(movieId: Int): Movie?
}

interface MovieApi {
    @GET("3/movie/top_rated?api_key=88903affb25acfffe2947acd6bfb75c1&language=en-US&page=1&region=ru")
    suspend fun getMovies(): MovieResponse
    @GET("3/movie/{movie_id}/credits?api_key=88903affb25acfffe2947acd6bfb75c1&language=en-US")
    suspend fun getActors(@Path("movie_id") movieId: Int): ActorResponse
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
        val genresMap = loadGenres()
//        val actorsMap = loadActors()

        val data = moviesApi.getMovies()
        println(data.results)
        return parseMovies(data.results, genresMap)//, actorsMap)
    }

//    private suspend fun loadMoviesFromJsonFile(): List<Movie> {
//        val genresMap = loadGenres()
//        val actorsMap = loadActors()
//
//        val data = readAssetFileToString("data.json")
//        return parseMovies(data, genresMap, actorsMap)
//    }


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
                imageUrl = jsonActor.profilePicture ?: ""
            )
        }
    }

    private suspend fun parseMovies(
        moviesData: List<JsonMovie>,
        genreData: List<Genre>//,
        //actors: List<Actor>
    ): List<Movie> {
        val genresMap = genreData.associateBy(Genre::id)
        //val actorsMap = actors.associateBy(Actor::id)

//        val jsonMovies = jsonFormat.decodeFromString<List<JsonMovie>>(string = jsonString)

        val picBaseUrl = "https://image.tmdb.org/t/p/"
        return moviesData.map { jsonMovie ->
            Movie(
                id = jsonMovie.id,
                name = jsonMovie.title,
                storyLine = jsonMovie.overview,
                imageCardUrl = picBaseUrl + "w185" + jsonMovie.posterPicture,
                imageDetailsUrl = picBaseUrl + "w500" + jsonMovie.backdropPicture,
                countStars = (jsonMovie.ratings / 2).toInt(),
                countReviews = jsonMovie.votesCount,
                rating = if (jsonMovie.adult) 16 else 13,
                duration = 13,//jsonMovie.runtime,
                genres = jsonMovie.genreIds.map { id ->
                    genresMap[id].orThrow { IllegalArgumentException("Genre not found") }
                },
                actors = moviesApi.getActors(jsonMovie.id).cast.subList(0, 10).map { jsonActor ->
                    Actor(
                        id = jsonActor.id,
                        name = jsonActor.name,
                        imageUrl = picBaseUrl + "w92" + jsonActor.profilePicture ?: ""
                    )
                },
//                actors = //jsonMovie.actors.
//                listOf<Int>(84433).map { id ->
//                    actorsMap[id].orThrow { IllegalArgumentException("Actor not found") }
//                },
                isLiked = when ((0..2).random()) {
                    0 -> false
                    else -> true
                }
            )
        }
    }
//    private fun parseMovies(
//        jsonString: String,
//        genreData: List<Genre>,
//        actors: List<Actor>
//    ): List<Movie> {
//        val genresMap = genreData.associateBy(Genre::id)
//        val actorsMap = actors.associateBy(Actor::id)
//
//        val jsonMovies = jsonFormat.decodeFromString<List<JsonMovie>>(string = jsonString)
//
//        return jsonMovies.map { jsonMovie ->
//            Movie(
//                id = jsonMovie.id,
//                name = jsonMovie.title,
//                storyLine = jsonMovie.overview,
//                imageCardUrl = jsonMovie.posterPicture,
//                imageDetailsUrl = jsonMovie.backdropPicture,
//                countStars = (jsonMovie.ratings / 2).toInt(),
//                countReviews = jsonMovie.votesCount,
//                rating = if (jsonMovie.adult) 16 else 13,
//                duration = 13,//jsonMovie.runtime,
//                genres = jsonMovie.genreIds.map { id ->
//                    genresMap[id].orThrow { IllegalArgumentException("Genre not found") }
//                },
//                actors = //jsonMovie.actors.
//                listOf<Int>(1, 2, 3).map { id ->
//                    actorsMap[id].orThrow { IllegalArgumentException("Actor not found") }
//                },
//                isLiked = when ((0..2).random()) {
//                    0 -> false
//                    else -> true
//                }
//            )
//        }
//    }

    override suspend fun loadMovie(movieId: Int): Movie? {
        val cachedMovies = movies ?: loadMovies()
        return cachedMovies.find { it.id == movieId }
    }

    private fun <T : Any> T?.orThrow(createThrowable: () -> Throwable): T {
        return this ?: throw createThrowable()
    }
}