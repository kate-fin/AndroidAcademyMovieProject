package com.example.androidacademymovieproject

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class FragmentMoviesList : Fragment() {

    var clickListener: ClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_list, container, false)

    override fun onStart() {
        super.onStart()
        view?.findViewById<View>(R.id.movie_card)?.apply {
            setOnClickListener {
                clickListener?.showMovieDetails()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ClickListener){
            clickListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        clickListener = null
    }

    interface ClickListener {
        fun showMovieDetails()
    }
}