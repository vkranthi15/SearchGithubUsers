package com.demo.searchgithubusers.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.demo.searchgithubusers.R
import com.demo.searchgithubusers.model.Items
import com.demo.searchgithubusers.ui.details.DetailsActivity

class Adapter(private val items: List<Items>, private val context: Context) : RecyclerView.Adapter<Adapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = items[position].name
        Glide.with(context)
                .load(items[position].avatar)
                .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_github)
                )
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.avatar)

        holder.itemView.setOnClickListener {
            val intent = DetailsActivity.open(context,items[position].name)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.name)
        var avatar: ImageView = itemView.findViewById(R.id.avatar)
    }
}
