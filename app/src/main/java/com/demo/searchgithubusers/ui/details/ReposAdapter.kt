package com.demo.searchgithubusers.ui.details

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.demo.searchgithubusers.R
import com.demo.searchgithubusers.model.Repos
import com.demo.searchgithubusers.ui.web.WebActivity

class ReposAdapter(private val items: List<Repos>?, private val context: Context) :
        RecyclerView.Adapter<ReposAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_repos, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.txtCountStar.text = items?.get(position)?.stargazersCount.toString().plus(" Forks")
        holder.txtCountForks.text = items?.get(position)?.forksCount.toString().plus(" Stars")
        holder.txtRepoName.text = items?.get(position)?.name.toString()

        holder.itemView.setOnClickListener {
            val intent = WebActivity.open(context, items?.get(position)?.svnUrl!!,
                    items[position].name)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items?.size!!
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtRepoName: TextView = itemView.findViewById(R.id.repoName)
        var txtCountStar: TextView = itemView.findViewById(R.id.txtCountStar)
        var txtCountForks: TextView = itemView.findViewById(R.id.txtCountForks)
    }
}
