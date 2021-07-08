package com.example.androidacademymovieproject.features.movie_details.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidacademymovieproject.MovieRepositoryProvider
import com.example.androidacademymovieproject.R
import com.example.androidacademymovieproject.data.NetworkRepositoryImpl
import com.example.androidacademymovieproject.features.movie_details.view_model.MovieDetailsViewModel
import com.example.androidacademymovieproject.features.movie_details.view_model.MovieDetailsViewModelFactory
import com.example.androidacademymovieproject.model.Movie
import kotlinx.coroutines.launch

class MovieDetailsFragment : Fragment() {

    private var clickListener: ClickListener? = null

    private val viewModel: MovieDetailsViewModel by viewModels {
        MovieDetailsViewModelFactory(
            (requireActivity() as MovieRepositoryProvider).provideMovieRepository()
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ClickListener) {
            clickListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieId: Int = arguments?.get(DATA_KEY) as Int
        val recycler = view.findViewById<RecyclerView>(R.id.recycler_view_actor)
        actionWithMovie(movieId, recycler)
        recycler.layoutManager = LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
        view.findViewById<View>(R.id.back_button)?.apply {
            setOnClickListener {
                clickListener?.closeMovieDetails()
            }
        }
    }

    private fun actionWithMovie(id: Int, recyclerView: RecyclerView) {
        lifecycleScope.launch {//ioScope
            viewModel.loadMovie(id)
            viewModel.movieLiveData.observe(viewLifecycleOwner, { movie ->
                movie?.let {
                    fillData(movie)
                    recyclerView.adapter = ActorAdapter(movie.actors)
                } ?: showErrorToast()
            })
        }
    }

    private fun showErrorToast() {
        lifecycleScope.launch {//uiScope
            Toast.makeText(
                context, "Проблемы с загрузкой фильма. Попробуйте позже",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun showBackPic(picUrl: String) {
        lifecycleScope.launch {//uiScope
            view?.let {
                view?.findViewById<ImageView>(R.id.pic_movie_back)?.let { it1 ->
                    Glide.with(it).load(picUrl).error(R.drawable.pic_movie_back)
                        .into(it1)
                }
            }
        }
    }

    private fun fillData(movie: Movie) {
        showBackPic(movie.imageDetailsUrl)
        view?.findViewById<TextView>(R.id.rating)?.text =
            context?.getString(R.string.movie_rating, movie.rating)
        view?.findViewById<TextView>(R.id.name_movie)?.text = movie.name
        view?.findViewById<TextView>(R.id.genre_movie)?.text =
            movie.genres.joinToString(", ") { it.name }
        view?.findViewById<TextView>(R.id.count_review)?.text =
            context?.getString(R.string.movie_count_reviews, movie.countReviews)
        view?.findViewById<TextView>(R.id.description)?.text = movie.storyLine
        val stars: List<ImageView?> = listOf(
            view?.findViewById(R.id.star1),
            view?.findViewById(R.id.star2),
            view?.findViewById(R.id.star3),
            view?.findViewById(R.id.star4),
            view?.findViewById(R.id.star5)
        )
        for (i in 0 until movie.countStars) {
            stars[i]?.setImageResource(R.drawable.pic_red_star_icon)
        }
        if (movie.actors.isEmpty()) {
            view?.findViewById<TextView>(R.id.cast)?.visibility = View.GONE
        }
    }


    override fun onDetach() {
        super.onDetach()
        clickListener = null
    }

    interface ClickListener {
        fun closeMovieDetails()
    }

    companion object {
        private const val DATA_KEY = "MOVIE_ID"
        fun newInstance(movieId: Int) = MovieDetailsFragment().also {
            val args = bundleOf(
                DATA_KEY to movieId
            )
            it.arguments = args
        }
    }
}