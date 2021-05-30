package com.example.androidacademymovieproject.model

import java.io.Serializable

data class Movie (
    val id: Int,
    val imageCardUrl: String,
    val name: String,
    val genres: List<Genre>,
    val rating: Int,
    val countReviews: Int,
    val imageDetailsUrl: String,
    val duration: Int,
    val storyLine: String,
    val actors: List<Actor>,
    val countStars: Int,
    val isLiked: Boolean
)//: Serializable