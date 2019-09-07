package com.demo.searchgithubusers.ui.web

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import com.demo.searchgithubusers.R

class WebActivity : AppCompatActivity() {
    private var mywebview: WebView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        val svnUrl = intent.getStringExtra(INTENT_URL)
        val repoName = intent.getStringExtra(INTENT_REPO)

        title = repoName
        mywebview = findViewById<WebView>(R.id.webview)
        mywebview!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        mywebview!!.loadUrl(svnUrl)
    }

    companion object {
        private val INTENT_URL: String? = "url"
        private val INTENT_REPO: String? = "repo"
        fun open(context: Context?, svnUrl: String, repoName: String): Intent {
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra(INTENT_URL, svnUrl)
            intent.putExtra(INTENT_REPO, repoName)
            return intent
        }
    }
}