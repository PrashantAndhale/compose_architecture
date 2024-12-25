package com.example.data.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.domain.model.MoviesItem

@Dao
interface MovieDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(list: List<MoviesItem>)

    @Query("SELECT * FROM MoviesItem")
    fun getAllMovies(): PagingSource<Int, MoviesItem>

    @Query("Delete  FROM MoviesItem")
    fun deleteAllMovies()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMoviesKey(list: List<MovieKey>)

    @Query("Delete  FROM MovieKey")
    fun deleteAllMoviesKey()

    @Query("SELECT * FROM MovieKey WHERE id=:id")
    fun getAllKeys(id:String): MovieKey

    @Query("SELECT * FROM MovieKey WHERE id = :id")
    suspend fun getMovieKey(id: Int): MovieKey?

}