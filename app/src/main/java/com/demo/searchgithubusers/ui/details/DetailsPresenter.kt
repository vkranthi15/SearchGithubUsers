package com.demo.searchgithubusers.ui.details

import com.demo.searchgithubusers.api.ApiClient
import com.demo.searchgithubusers.api.ApiInterface
import com.demo.searchgithubusers.model.Repos
import com.demo.searchgithubusers.model.UserDetailModel

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsPresenter(private val initView: DetailsView.InitView) : DetailsView.getRepo, DetailsView.getDetail {

    override fun getUsersDetail(userName: String) {
        initView.showLoading()
        val apiInterface = ApiClient.apiClient!!.create(ApiInterface::class.java)
        val call = apiInterface.getUsersDetail(userName)
        call.enqueue(object : Callback<UserDetailModel> {
            override fun onResponse(call: Call<UserDetailModel>, response: Response<UserDetailModel>) {
                initView.hideLoading()
                initView.userDetails(response.body()!!)
            }

            override fun onFailure(call: Call<UserDetailModel>, t: Throwable) {
                initView.hideLoading()
                t.printStackTrace()
            }
        })
    }

    override fun getReposList(userName: String) {
        initView.showLoading()
        val apiInterface = ApiClient.apiClient!!.create(ApiInterface::class.java)
        val call = apiInterface.getUsersRepos(userName)
        call.enqueue(object : Callback<ArrayList<Repos>> {
            override fun onFailure(call: Call<ArrayList<Repos>>, t: Throwable) {
                initView.hideLoading()
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ArrayList<Repos>>, response: Response<ArrayList<Repos>>) {
                initView.hideLoading()
                initView.repoList(response.body())
            }
        })
    }
}
