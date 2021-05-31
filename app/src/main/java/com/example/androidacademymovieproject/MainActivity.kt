package com.example.androidacademymovieproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.androidacademymovieproject.model.Movie
import com.example.androidacademymovieproject.view.FragmentMovieDetails
import com.example.androidacademymovieproject.view.FragmentMoviesList

class MainActivity : AppCompatActivity(), FragmentMoviesList.ClickListener,
    FragmentMovieDetails.ClickListener
{

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
                    FragmentMoviesList(),
                    FragmentMoviesList::class.java.simpleName
                )
                    .addToBackStack("${FragmentMoviesList()}")
                    .commit()
            }
    }

    override fun showMovieDetails(movieId: Int) {
        supportFragmentManager.beginTransaction()
            .apply {
                replace(
                    R.id.main_frame_layout,
                    FragmentMovieDetails.newInstance(movieId),
                    FragmentMovieDetails::class.java.simpleName
                )
                    .addToBackStack("${FragmentMovieDetails()}")
                    .commit()
            }
    }

    override fun closeMovieDetails() {
        supportFragmentManager.popBackStack()
    }

}