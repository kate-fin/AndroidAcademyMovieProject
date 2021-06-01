package com.example.androidacademymovieproject.view

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidacademymovieproject.model.Data
import com.example.androidacademymovieproject.R
import com.example.androidacademymovieproject.data.JsonMovieRepository
import com.example.androidacademymovieproject.model.Movie
import com.example.androidacademymovieproject.presenter.MovieAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentMoviesList : Fragment() {

    var clickListener: ClickListener? = null
    private val ioScope = CoroutineScope(Dispatchers.IO)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_movie)
        val spanCount =
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 4
        recyclerView.layoutManager = GridLayoutManager(view.context, spanCount)
        loadDataToAdapter(recyclerView)
    }

    private fun loadDataToAdapter(recyclerView: RecyclerView) {
        lifecycleScope.launch {//ioScope
            val movies = JsonMovieRepository(requireContext()).loadMovies()
            recyclerView.adapter =
                MovieAdapter(movies) { movie ->
                    clickListener?.showMovieDetails(movie)
                }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ClickListener) {
            clickListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        clickListener = null
    }

    interface ClickListener {
        fun showMovieDetails(movieId: Int)
    }
}