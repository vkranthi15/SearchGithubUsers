package com.demo.searchgithubusers.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.demo.searchgithubusers.R
import com.demo.searchgithubusers.Utils

import com.demo.searchgithubusers.model.Repos
import com.demo.searchgithubusers.model.UserDetailModel
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity(), DetailsView.InitView {
    private var recyclerView: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private var detailsPresenter: DetailsPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val userName = intent.getStringExtra(INTENT_USER_NAME).also {
            title = it
        }
        init()
        detailsPresenter = DetailsPresenter(this)
        detailsPresenter!!.getUsersDetail(userName)
        detailsPresenter!!.getReposList(userName)
    }

    companion object {
        private val INTENT_USER_NAME: String? = ""
        fun open(context: Context, userName: String?): Intent {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(INTENT_USER_NAME, userName)
            return intent
        }
    }

    private fun init() {
        progressBar = findViewById(R.id.progress)
        recyclerView = findViewById(R.id.recyclerRepos)
        val layoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.setHasFixedSize(true)
    }


    override fun showLoading() {
        progressBar!!.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar!!.visibility = View.INVISIBLE
    }

    override fun userDetails(userDetailModel: UserDetailModel) {
        Log.e("userDetailModel ", userDetailModel.toString())
        txtTitleName.text = userDetailModel.login
        if (!TextUtils.isEmpty(userDetailModel.email)) {
            txtEmail.text = "Email: ".plus(userDetailModel.email)
        } else {
            txtEmail.visibility = View.GONE
        }

        if (!TextUtils.isEmpty(userDetailModel.location)) {
            txtLocation.text = "Location: ".plus(userDetailModel.location)
        } else {
            txtLocation.visibility = View.GONE
        }

        txtJoindate.text = "Join Date: ".plus(userDetailModel.createdAt.split("T")[0])
        followersCount.text = userDetailModel.followers.toString().plus(" Followers")
        followingsCount.text = "Following: ".plus(userDetailModel.following.toString())
        Glide.with(this)
                .load(userDetailModel.avatarUrl)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_github)
                )
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(avatar)
    }


    override fun repoList(repos: List<Repos>?) {
        val adapter = ReposAdapter(repos, this)
        recyclerView?.adapter = adapter
    }
}
