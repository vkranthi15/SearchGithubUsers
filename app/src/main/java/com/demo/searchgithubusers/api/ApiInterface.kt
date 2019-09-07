package com.demo.searchgithubusers.api

import com.demo.searchgithubusers.model.Repos
import com.demo.searchgithubusers.model.UserDetailModel
import com.demo.searchgithubusers.model.Users

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("search/users")
    fun getUsers(@Query("q") keyword: String): Call<Users>

    @GET("users/{user}")
    fun getUsersDetail(@Path(value = "user", encoded = true) user: String): Call<UserDetailModel>

    @GET("users/{user}/repos")
    fun getUsersRepos(@Path(value = "user", encoded = true) user: String): Call<ArrayList<Repos>>
}