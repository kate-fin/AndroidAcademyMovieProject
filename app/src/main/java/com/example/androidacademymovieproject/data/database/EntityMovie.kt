package com.example.androidacademymovieproject.data.database

import androidx.room.*
import com.example.androidacademymovieproject.model.Actor

@Entity(tableName = DbContract.Movie.TABLE_NAME)
data class EntityMovie(
    @PrimaryKey
    @ColumnInfo(name = DbContract.Movie.COLUMN_NAME_ID) val id: Int,
    @ColumnInfo(name = DbContract.Movie.COLUMN_NAME_IMAGE_CARD_URL) val imageCardUrl: String,
    @ColumnInfo(name = DbContract.Movie.COLUMN_NAME_TITLE) val name: String,
    @ColumnInfo(name = DbContract.Movie.COLUMN_NAME_GENRES) val genres: String,
    @ColumnInfo(name = DbContract.Movie.COLUMN_NAME_RATING) val rating: Int,
    @ColumnInfo(name = DbContract.Movie.COLUMN_NAME_COUNT_REVIEWS) val countReviews: Int,
    @ColumnInfo(name = DbContract.Movie.COLUMN_NAME_IMAGE_DETAILS_URL) val imageDetailsUrl: String,
    @ColumnInfo(name = DbContract.Movie.COLUMN_NAME_DURATION) val duration: Int,
    @ColumnInfo(name = DbContract.Movie.COLUMN_NAME_STORY_LINE) val storyLine: String,
//    @Embedded
//    @Relation(parentColumn = DbContract.Movie.COLUMN_NAME_ID,
//        entityColumn = DbContract.Actor.COLUMN_NAME_MOVIE_FK)
//    val actors: List<EntityActor>,
    @ColumnInfo(name = DbContract.Movie.COLUMN_NAME_COUNT_STARS) val countStars: Int,
    @ColumnInfo(name = DbContract.Movie.COLUMN_NAME_IS_LIKED) val isLiked: Boolean
)
