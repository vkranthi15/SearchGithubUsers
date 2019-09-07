package com.demo.searchgithubusers.ui

import com.demo.searchgithubusers.api.ApiClient
import com.demo.searchgithubusers.api.ApiInterface
import com.demo.searchgithubusers.model.Users

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter(private val initView: MainView.InitView) : MainView.GetUsers, MainView.getDetail {

    override fun getUserList(keyword: String) {
        initView.showLoading()
        val apiInterface = ApiClient.apiClient!!.create(ApiInterface::class.java)
        val call = apiInterface.getUsers(keyword)
        call.enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {

                initView.hideLoading()
                response.body()!!.items?.let { initView.userList(it) }
                val totalCount = response.body()!!.totalCount

                if (!response.isSuccessful || response.body()!!.items == null || totalCount == 0) {
                    initView.userListFailure("No Result for '$keyword'", "Try Searching for Other Users")
                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                initView.userListFailure("Error Loading For '$keyword'", t.toString())
                initView.hideLoading()
                t.printStackTrace()
            }
        })
    }

    override fun getUsersDetail(userName: String) {
        initView.showLoading()
        val apiInterface = ApiClient.apiClient!!.create(ApiInterface::class.java)
        val call = apiInterface.getUsers(userName)
        call.enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {

                initView.hideLoading()
                initView.userList(response.body()!!.items)
                val totalCount = response.body()!!.totalCount

                if (!response.isSuccessful || response.body()!!.items == null || totalCount == 0) {
                    initView.userListFailure("No Result for '$userName'", "Try Searching for Other Users")
                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                initView.userListFailure("Error Loading For '$userName'", t.toString())
                initView.hideLoading()
                t.printStackTrace()
            }
        })
    }
}
