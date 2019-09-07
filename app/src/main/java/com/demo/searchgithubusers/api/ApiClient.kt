package com.demo.searchgithubusers.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private val BASE_URL = "https://api.github.com/"
    private var retrofit: Retrofit? = null

    val apiClient: Retrofit?
        get() {

            if (retrofit == null) {

                retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

            }

            return retrofit
        }
}
