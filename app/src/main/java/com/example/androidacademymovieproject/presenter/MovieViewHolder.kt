package com.example.androidacademymovieproject.presenter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
    fun bind(movie: Movie, clickedCard: (itemId: Int) -> Unit) {
        Glide.with(itemView)
            .load(movie.imageCardUrl)
            .error(R.drawable.pic_movie_back_card)
            .placeholder(R.drawable.pic_movie_back_card)
            .into(image)
        name.text = movie.name
        genre.text = movie.genres.joinToString(", ") { it.name }
        itemView.context.apply {
            rating.text = getString(R.string.movie_rating, movie.rating)
            countReviews.text = getString(R.string.movie_count_reviews, movie.countReviews)
            duration.text = getString(R.string.movie_duration, movie.duration)
        }

        itemView.setOnClickListener {
            clickedCard(movie.id)
        }
    }
}