package com.example.androidacademymovieproject.data.database

import androidx.room.Embedded
import androidx.room.Relation

data class MovieResponse(
    @Embedded
    val movie: EntityMovie,
    @Relation(parentColumn = DbContract.Movie.COLUMN_NAME_ID,
        entityColumn = DbContract.Actor.COLUMN_NAME_MOVIE_FK)
    val actors: List<EntityActor>
)
