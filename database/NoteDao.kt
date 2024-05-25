package com.example.submission1.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface NoteDao {
    @Query("SELECT * FROM news ORDER BY username DESC")
    fun getNews(): LiveData<List<Note>>

    @Query("SELECT * FROM news where bookmarked = 1")
    fun getBookmarkedNews(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNews(news: List<Note>)

    @Update
    fun updateNews(news: Note)

    @Query("DELETE FROM news WHERE bookmarked = 0")
    fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM news WHERE username = :title AND bookmarked = 1)")
    fun isNewsBookmarked(title: String): Boolean

}