package com.example.androidacademymovieproject.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ResponseMovie(
    val page:Int,
    val results: List<JsonMovie>
)
