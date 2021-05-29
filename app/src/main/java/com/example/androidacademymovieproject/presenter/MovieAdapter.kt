package com.example.androidacademymovieproject.presenter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidacademymovieproject.R
import com.example.androidacademymovieproject.model.Movie

class MovieAdapter(private val data: List<Movie>, private val clickedCard: (item: Movie) -> Unit):
    RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_holder_movie,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position), clickedCard)
    }

    private fun getItem(position: Int): Movie{
        return data[position]
    }

    override fun getItemCount(): Int {
        return data.size
    }
}