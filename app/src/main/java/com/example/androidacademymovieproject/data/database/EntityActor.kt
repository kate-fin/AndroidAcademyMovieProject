package com.example.androidacademymovieproject.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = DbContract.Actor.TABLE_NAME)
data class EntityActor(
    @PrimaryKey
    @ColumnInfo(name = DbContract.Actor.COLUMN_NAME_ID) val id: Int,
    @ColumnInfo(name = DbContract.Actor.COLUMN_NAME_ACTOR_NAME) val name: String,
    @ColumnInfo(name = DbContract.Actor.COLUMN_NAME_IMAGE_URL) val imageUrl: String,
    @ForeignKey(
        entity = EntityMovie::class, parentColumns = [DbContract.Movie.COLUMN_NAME_ID],
            childColumns = [DbContract.Actor.COLUMN_NAME_MOVIE_FK], onDelete = ForeignKey.NO_ACTION
    )
    @ColumnInfo(name = DbContract.Actor.COLUMN_NAME_MOVIE_FK) val movieId: Int
)
