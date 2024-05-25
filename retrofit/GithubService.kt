package com.example.submission1.retrofit

import com.example.submission1.data.response.DetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {
    @GET("users/{username}")
    fun getUser(@Path("username") username: String): Call<DetailResponse>
}