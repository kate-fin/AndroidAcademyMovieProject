package com.example.androidacademymovieproject.presenter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidacademymovieproject.R
import com.example.androidacademymovieproject.model.Actor

class ActorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val image = itemView.findViewById<ImageView>(R.id.actor_image)
    val text = itemView.findViewById<TextView>(R.id.actor_text)

    fun bind(actor: Actor){
        image.setImageResource(actor.image)
        text.text = actor.name
    }
}