package com.example.submission1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission1.data.response.DetailResponse
import com.example.submission1.retrofit.GithubService
import com.example.submission1.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {
    private val _userData = MutableLiveData<DetailResponse>()
    val userData: LiveData<DetailResponse>
        get() = _userData

    private val apiService = RetrofitClient.retrofit.create(GithubService::class.java)

    fun fetchUserData(username: String) {
        val call = apiService.getUser(username)
        call.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(call: Call<DetailResponse>, response: Response<DetailResponse>) {
                if (response.isSuccessful) {
                    _userData.value = response.body()
                } else {

                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {

            }
        })
    }
}