package com.example.androidacademymovieproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity(), FragmentMoviesList.ClickListener,
    FragmentMovieDetails.ClickListener {

    lateinit var shownFragment: Fragment
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
                replace(R.id.main_frame_layout, FragmentMoviesList())
                    .addToBackStack("${FragmentMoviesList()}")
                    .commit()
            }
    }

    override fun showMovieDetails() {
        supportFragmentManager.beginTransaction()
            .apply {
                replace(R.id.main_frame_layout, FragmentMovieDetails())
                    .addToBackStack("${FragmentMovieDetails()}")
                    .commit()
            }
    }

    override fun closeMovieDetails() {
        supportFragmentManager.popBackStack()
    }


}