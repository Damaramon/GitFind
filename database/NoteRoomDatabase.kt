package com.example.submission1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteRoomDatabase : RoomDatabase() {
    abstract fun newsDao(): NoteDao

    companion object {
        @Volatile
        private var instance: NoteRoomDatabase? = null
        fun getInstance(context: Context): NoteRoomDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    NoteRoomDatabase::class.java, "News.db"
                ).build()
            }
    }
}