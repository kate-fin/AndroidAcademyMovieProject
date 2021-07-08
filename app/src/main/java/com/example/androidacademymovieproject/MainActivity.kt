package com.example.androidacademymovieproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.androidacademymovieproject.data.MovieRepository
import com.example.androidacademymovieproject.data.MovieRepositoryImpl
import com.example.androidacademymovieproject.data.NetworkRepositoryImpl
import com.example.androidacademymovieproject.data.database.DbRepository
import com.example.androidacademymovieproject.data.database.DbRepositoryImpl
import com.example.androidacademymovieproject.data.database.MovieDatabase
import com.example.androidacademymovieproject.data.network.NetworkRepository
import com.example.androidacademymovieproject.features.movie_details.view.MovieDetailsFragment
import com.example.androidacademymovieproject.features.movies_list.view.MoviesListFragment

class MainActivity : AppCompatActivity(),
    MoviesListFragment.ClickListener,
    MovieDetailsFragment.ClickListener,
    MovieRepositoryProvider {

    private val remoteRepository: NetworkRepository = NetworkRepositoryImpl()

    //    private val localRepository: DbRepository = DbRepositoryImpl(MovieApp.db)
    lateinit var movieRepository: MovieRepository //= MovieRepositoryImpl(localRepository,
//     remoteRepository)

    override fun provideMovieRepository(): MovieRepository {
        println("5")
        return movieRepository
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MY MESS", "I STARTED HERE!!!!!!!!!!!!!!!")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println("1")
        val db = MovieDatabase.getInstance(this)
        println("2")
        val localRepository: DbRepository = DbRepositoryImpl(db)
        println("3")
        movieRepository = MovieRepositoryImpl(localRepository, remoteRepository)
        println("4")

        if (savedInstanceState == null) {
            showMoviesList()
        }

    }

    private fun showMoviesList() {
        supportFragmentManager.beginTransaction()
            .apply {
                replace(
                    R.id.main_frame_layout,
                    MoviesListFragment(),
                    MoviesListFragment::class.java.simpleName
                )
                    .addToBackStack("${MoviesListFragment()}")
                    .commit()
            }
    }

    override fun showMovieDetails(movieId: Int) {
        supportFragmentManager.beginTransaction()
            .apply {
                replace(
                    R.id.main_frame_layout,
                    MovieDetailsFragment.newInstance(movieId),
                    MovieDetailsFragment::class.java.simpleName
                )
                    .addToBackStack("${MovieDetailsFragment()}")
                    .commit()
            }
    }

    override fun closeMovieDetails() {
        supportFragmentManager.popBackStack()
    }

}