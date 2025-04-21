package com.example.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.domain.model.Newspapers

@Database(entities = [Newspapers::class, NewsKey::class], version = 1, exportSchema = false)
abstract class NewsDataBase : RoomDatabase() {

    companion object {
        fun getInstance(context: Context): NewsDataBase {
            return Room.databaseBuilder(context, NewsDataBase::class.java, "news").build()
        }
    }

    abstract fun getNewsDAO(): NewsDAO
}