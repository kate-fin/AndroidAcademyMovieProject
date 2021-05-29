package com.example.androidacademymovieproject.model

import java.io.Serializable

data class Movie (
    val image: Int,
    val name: String,
    val genre: String,
    val rating: String,
    val countReviews: String,
    val duration: String,
    val storyLine: String,
    val actors: List<Actor>
//    val countStars: String,
//    val like: Boolean
): Serializable