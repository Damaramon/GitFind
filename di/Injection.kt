package com.example.submission1.di

import android.content.Context
import com.example.submission1.AppExecutors
import com.example.submission1.data.NewsRepository
import com.example.submission1.data.ui.ViewModelFactory
import com.example.submission1.database.NoteRoomDatabase
import com.example.submission1.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): NewsRepository {
        val apiService = ApiConfig.getApiService()
        val database = NoteRoomDatabase.getInstance(context)
        val dao = database.newsDao()
        val appExecutors = AppExecutors()
        return NewsRepository.getInstance(apiService, dao, appExecutors)
    }

}