package com.example.submission1.data.ui

import androidx.lifecycle.ViewModel
import com.example.submission1.data.NewsRepository
import com.example.submission1.database.Note

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    fun getHeadlineNews() = newsRepository.getHeadlineNews()

    fun getBookmarkedNews() = newsRepository.getBookmarkedNews()
    fun saveNews(news: Note) {
        newsRepository.setBookmarkedNews(news, true)
    }
    fun deleteNews(news: Note) {
        newsRepository.setBookmarkedNews(news, false)
    }
}