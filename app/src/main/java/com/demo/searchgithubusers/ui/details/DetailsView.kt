package com.demo.searchgithubusers.ui.details

import com.demo.searchgithubusers.model.Repos
import com.demo.searchgithubusers.model.UserDetailModel

interface DetailsView {

    interface InitView {

        fun showLoading()
        fun hideLoading()
        fun userDetails(userDetailModel: UserDetailModel)
        fun repoList(users: List<Repos>?)
    }

    interface getDetail {
        fun getUsersDetail(userName: String)
    }

    interface getRepo {
        fun getReposList(userName: String)
    }
}
