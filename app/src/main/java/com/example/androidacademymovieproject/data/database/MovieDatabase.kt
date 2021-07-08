package com.example.androidacademymovieproject.data.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [EntityMovie::class, EntityActor::class], version = 1)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): Dao

    companion object {
        //        fun getInstance(appContext: Context): MovieDatabase {
//            return Room.databaseBuilder(appContext, MovieDatabase::class.java, DbContract.DB_NAME)
//                .allowMainThreadQueries()
//                .fallbackToDestructiveMigration()
//                .build()
//        }
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase {
            if (INSTANCE == null) {
                synchronized(MovieDatabase::class.java) {
                    if (INSTANCE == null) {
                        Log.d("TTT", "Creating new database instance")
                        INSTANCE = Room.databaseBuilder(
                            context,
                            MovieDatabase::class.java,
                            DbContract.DB_NAME
                        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
                    }
                }
            }
            Log.d("TTT", "Getting the database instance")
            return INSTANCE!!
        }

    }
}