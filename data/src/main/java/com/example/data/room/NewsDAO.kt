package com.example.data.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.domain.model.Newspapers

@Dao
interface NewsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllNews(list: List<Newspapers>)

    @Query("SELECT * FROM Newspapers")
    fun getAllNews(): PagingSource<Int, Newspapers>

    @Query("Delete  FROM Newspapers")
    fun deleteAllNews()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllNewsKey(list: List<NewsKey>)

    @Query("Delete  FROM NewsKey")
    fun deleteAllNewsKey()

    @Query("SELECT * FROM NewsKey WHERE id=:id")
    fun getAllKeys(id:String): NewsKey

    @Query("SELECT * FROM NewsKey WHERE id = :id")
    suspend fun getNewsKey(id: Int): NewsKey?

}