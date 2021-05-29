package com.example.androidacademymovieproject.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidacademymovieproject.R
import com.example.androidacademymovieproject.model.Movie
import com.example.androidacademymovieproject.presenter.ActorAdapter

class FragmentMovieDetails : Fragment() {

    var clickListener: ClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie: Movie = arguments?.get(DATA_KEY) as? Movie ?: return
        fillData(movie)
        val recycler = view.findViewById<RecyclerView>(R.id.recycler_view_actor)
        recycler.adapter = ActorAdapter(movie.actors)
        recycler.layoutManager = LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
        view.findViewById<View>(R.id.back_button)?.apply {
            setOnClickListener{
                clickListener?.closeMovieDetails()
            }
        }
    }

    private fun fillData(movie: Movie) {
        view?.findViewById<TextView>(R.id.rating)?.text = movie.rating
        view?.findViewById<TextView>(R.id.name_movie)?.text = movie.name
        view?.findViewById<TextView>(R.id.genre_movie)?.text = movie.genre
        view?.findViewById<TextView>(R.id.count_review)?.text = movie.countReviews
        view?.findViewById<TextView>(R.id.description)?.text = movie.storyLine
//        view?.findViewById<ImageView>(R.id.pic_movie_back)?.setImageResource(movie.image)
    }

//    override fun onStart() {
//        super.onStart()
//
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

    interface ClickListener{
        fun closeMovieDetails()
    }

    companion object{
        private const val DATA_KEY = "MOVIE_DATA"
        fun newInstance(movie: Movie)//: FragmentMovieDetails{
//            val bundle = bundleOf(
//                DATA_KEY to movie
//            )
//            val frag = FragmentMovieDetails()
//            frag.arguments = bundle
//            return frag
//        }
                = FragmentMovieDetails().also {
            val args = bundleOf(
                DATA_KEY to movie
            )
            it.arguments = args
        }
    }
}