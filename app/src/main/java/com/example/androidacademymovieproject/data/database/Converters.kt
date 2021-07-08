package com.example.androidacademymovieproject.data.database

import androidx.room.TypeConverter
import com.example.androidacademymovieproject.model.Genre

class Converters {
    @TypeConverter
    fun fromListToString(genres: List<Genre>):String {
        return genres.joinToString(","){genre -> genre.name }
    }

    @TypeConverter
    fun fromStringToList(genres: String):List<Genre>{
        return genres.split(",").map { Genre(1, it) }
    }
}