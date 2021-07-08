package com.example.androidacademymovieproject.features.movie_details.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidacademymovieproject.R
import com.example.androidacademymovieproject.model.Actor

class ActorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val image: ImageView = itemView.findViewById(R.id.actor_image)
    val name: TextView = itemView.findViewById(R.id.actor_text)

    fun bind(actor: Actor) {
        Glide.with(itemView).load(actor.imageUrl).error(R.drawable.pic_actor_1)
            .placeholder(R.drawable.pic_actor_1).into(image)
        name.text = actor.name
    }
}