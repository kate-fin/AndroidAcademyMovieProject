package com.example.androidacademymovieproject.data.database

object DbContract {
    const val DB_NAME = "Movie.db"

    object Movie{
        const val TABLE_NAME = "movie"
        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_IMAGE_CARD_URL = "imageCardUrl"
        const val COLUMN_NAME_TITLE = "name"
        const val COLUMN_NAME_GENRES = "genres"
        const val COLUMN_NAME_RATING = "rating"
        const val COLUMN_NAME_COUNT_REVIEWS = "countReviews"
        const val COLUMN_NAME_IMAGE_DETAILS_URL = "imageDetailsUrl"
        const val COLUMN_NAME_DURATION = "duration"
        const val COLUMN_NAME_STORY_LINE = "storyLine"
        const val COLUMN_NAME_ACTORS = "actors"
        const val COLUMN_NAME_COUNT_STARS = "countStars"
        const val COLUMN_NAME_IS_LIKED = "isLiked"
    }

    object Actor{
        const val TABLE_NAME = "actor"
        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_ACTOR_NAME = "name"
        const val COLUMN_NAME_IMAGE_URL = "imageUrl"
        const val COLUMN_NAME_MOVIE_FK = "movieId"
    }
}