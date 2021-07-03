package com.example.androidacademymovieproject.data

import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    val page:Int,
    val results: List<JsonMovie>
)
