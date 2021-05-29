package com.example.androidacademymovieproject.presenter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidacademymovieproject.R
import com.example.androidacademymovieproject.model.Movie

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val image = itemView.findViewById<ImageView>(R.id.pic_movie_back_card)
    private val name = itemView.findViewById<TextView>(R.id.title_card)
    private val genre = itemView.findViewById<TextView>(R.id.genre_movie_card)
    private val rating = itemView.findViewById<TextView>(R.id.rating_card)
    private val countReviews = itemView.findViewById<TextView>(R.id.count_review_card)
    private val duration = itemView.findViewById<TextView>(R.id.duration)


    //    val countStars = itemView.findViewById<Te>()
//    val like
    fun bind(movie: Movie, clickedCard:(item: Movie) -> Unit) {
        image.setImageResource(movie.image)
        name.text = movie.name
        genre.text = movie.genre
        rating.text = movie.rating
        countReviews.text = movie.countReviews
        duration.text = movie.duration
        itemView.setOnClickListener {
            clickedCard(movie)
        }
    }
}