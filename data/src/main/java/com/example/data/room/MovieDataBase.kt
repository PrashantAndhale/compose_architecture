package com.example.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
 import com.example.domain.model.MoviesItem

@Database(entities = [MoviesItem::class, MovieKey::class], version = 1, exportSchema = false)
abstract class MovieDataBase : RoomDatabase() {

    companion object {
        fun getInstance(context: Context): MovieDataBase {
            return Room.databaseBuilder(context, MovieDataBase::class.java, "movie").build()
        }
    }

    abstract fun getMovieDAO(): MovieDAO
}