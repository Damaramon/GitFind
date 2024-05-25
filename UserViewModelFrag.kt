package com.example.submission1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission1.data.response.ItemsItem
import com.example.submission1.retrofit.ApiService
import com.example.submission1.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModelFrag : ViewModel() {

    private val githubApiService = RetrofitClient.retrofit.create(ApiService::class.java)

    private val _userList = MutableLiveData<List<ItemsItem>>()
    val userList: LiveData<List<ItemsItem>>
        get() = _userList

    fun fetchFollowers(username: String) {
        githubApiService.getFollowers(username).enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                if (response.isSuccessful) {
                    _userList.value = response.body()
                } else {

                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {

            }
        })
    }

    fun fetchFollowing(username: String) {
        githubApiService.getFollowing(username).enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                if (response.isSuccessful) {
                    _userList.value = response.body()
                } else {

                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {

            }
        })
    }
}
