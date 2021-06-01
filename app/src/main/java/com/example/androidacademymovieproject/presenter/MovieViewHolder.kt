package com.example.androidacademymovieproject.presenter

import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
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
    private val stars: List<ImageView?> = listOf(
        itemView.findViewById(R.id.star1),
        itemView.findViewById(R.id.star2),
        itemView.findViewById(R.id.star3),
        itemView.findViewById(R.id.star4),
        itemView.findViewById(R.id.star5)
    )
    private val like = itemView.findViewById<ImageView>(R.id.like)

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
        for (i in 0 until movie.countStars){
            stars[i]?.setImageResource(R.drawable.pic_red_star_icon)
        }
        val likeColor = if (movie.isLiked) R.color.pink_light else R.color.white
        ImageViewCompat.setImageTintList(
            like, ColorStateList.valueOf(ContextCompat.getColor(like.context, likeColor))
        )
    }
}