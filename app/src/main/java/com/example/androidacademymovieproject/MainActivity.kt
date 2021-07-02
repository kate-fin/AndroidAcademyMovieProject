package com.example.androidacademymovieproject

import MovieRepositoryProvider
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidacademymovieproject.data.JsonMovieRepository
import com.example.androidacademymovieproject.data.MovieRepository
import com.example.androidacademymovieproject.features.movie_details.MovieDetailsFragment
import com.example.androidacademymovieproject.features.movies_list.MoviesListFragment

class MainActivity : AppCompatActivity(), MoviesListFragment.ClickListener,
    MovieDetailsFragment.ClickListener
,MovieRepositoryProvider
{
    private val jsonMovieRepository = JsonMovieRepository(this)
    override fun provideMovieRepository(): MovieRepository = jsonMovieRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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