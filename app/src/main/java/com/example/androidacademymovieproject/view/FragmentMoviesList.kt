package com.example.androidacademymovieproject.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidacademymovieproject.model.Data
import com.example.androidacademymovieproject.R
import com.example.androidacademymovieproject.model.Movie
import com.example.androidacademymovieproject.presenter.MovieAdapter

class FragmentMoviesList : Fragment() {

    var clickListener: ClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_movie)
        recyclerView.adapter =
            MovieAdapter(Data.getData()) { movieData ->
                clickListener?.showMovieDetails(movieData)
            }
        val spanCount = 2
        recyclerView.layoutManager = GridLayoutManager(view.context, spanCount)
    }

//    override fun onStart() {
//        super.onStart()
//        view?.findViewById<View>(R.id.movie_card)?.apply {
//            setOnClickListener {
//                clickListener?.showMovieDetails()
//            }
//        }
//    }

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
        fun showMovieDetails(data: Movie)
    }
}