package com.example.androidacademymovieproject.data

import kotlinx.serialization.Serializable

@Serializable
data class ResponseMovie(
    val page:Int,
    val results: List<JsonMovie>
)
