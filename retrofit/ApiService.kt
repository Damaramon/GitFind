package com.example.submission1.retrofit

import com.example.submission1.data.response.ItemsItem
import com.example.submission1.data.response.ProfResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
 
    @GET("search/users")
    fun getListUsers(@Query("q") page: String): Call<ProfResponse>
    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>
    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}