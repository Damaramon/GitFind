package com.example.submission1.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.submission1.AppExecutors
import com.example.submission1.BuildConfig
import com.example.submission1.data.response.ProfResponse
import com.example.submission1.database.Note
import com.example.submission1.database.NoteDao
import com.example.submission1.database.Result
import com.example.submission1.retrofit.ApiService



import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
private const val ARG_USERNAME = "username"
class NewsRepository private constructor(
    private val apiService: ApiService,
    private val newsDao: NoteDao,
    private val appExecutors: AppExecutors
) {   private lateinit var noteee: Note
    private var username: String = "dam"
    private val result = MediatorLiveData<Result<List<Note>>>()
    fun getBookmarkedNews(): LiveData<List<Note>> {
        return newsDao.getBookmarkedNews()
    }
    fun setBookmarkedNews(news: Note, bookmarkState: Boolean) {
        appExecutors.diskIO.execute {
            news.isBookmarked = bookmarkState
            newsDao.updateNews(news)
        }
    }
    fun getHeadlineNews(): LiveData<Result<List<Note>>> {
        noteee = Note(
            username = "Contoh Username",
            avatarUrl = "https://contoh.com/avatar.png",
            isBookmarked = false// atau false, sesuai kebutuhan
        )
        result.value = Result.Loading
        val client = apiService.getListUsers(username)
        client.enqueue(object : Callback<ProfResponse> {
            override fun onResponse(call: Call<ProfResponse>, response: Response<ProfResponse>) {
                if (response.isSuccessful) {
                    Log.d("NewsRepository", "Adacuyyyyyyyyy")
                    val articles = response.body()?.items
                    val newsList = ArrayList<Note>()
                    appExecutors.diskIO.execute {
                        articles?.forEach { article ->
                            val isBookmarked = newsDao.isNewsBookmarked(article.login)
                            val note = Note(
                                article.login,
                                article.avatarUrl,
                                isBookmarked
                            )
                            newsList.add(noteee)
                        }
                        newsDao.deleteAll()
                        newsDao.insertNews(newsList)
                    }
                }
                else {
                    Log.d("NewsRepository", "Login artikel kosong atau null")
                }
            }

            override fun onFailure(call: Call<ProfResponse>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }
        })
        val localData = newsDao.getNews()
        result.addSource(localData) { newData: List<Note> ->
            result.value = Result.Success(newData)
        }
        return result
    }

    companion object {
        @Volatile
        private var instance: NewsRepository? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: NoteDao,
            appExecutors: AppExecutors
        ): NewsRepository =
            instance ?: synchronized(this) {
                instance ?: NewsRepository(apiService, newsDao, appExecutors)
            }.also { instance = it }
    }
}
