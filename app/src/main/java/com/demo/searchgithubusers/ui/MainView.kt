package com.demo.searchgithubusers.ui

import com.demo.searchgithubusers.model.Items

interface MainView {

    interface InitView {

        fun showLoading()
        fun hideLoading()
        fun userList(users: List<Items>?)
        fun userListFailure(errorMessage: String, keyword: String)
    }

    interface GetUsers {
        fun getUserList(keyword: String)
    }

    interface getDetail {
        fun getUsersDetail(userName: String)
    }
}
