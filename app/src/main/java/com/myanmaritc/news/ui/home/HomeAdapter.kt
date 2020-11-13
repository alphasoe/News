package com.myanmaritc.news.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myanmaritc.news.R
import com.myanmaritc.news.model.ArticlesItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_news.view.*

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    var articlesList: List<ArticlesItem> = ArrayList()

    private var onClickListener: OnClickListener? = null

    inner class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        lateinit var articlesItem: ArticlesItem

        fun bind(articlesItem: ArticlesItem) {
            this.articlesItem = articlesItem
            itemView.author.text = articlesItem.author
            itemView.description.text = articlesItem.description

            Picasso.get().load(articlesItem.urlToImage).into(itemView.newsImage)
        }

        override fun onClick(v: View?) {
            onClickListener?.onClick(articlesItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int = articlesList.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(articlesList[position])
    }

    fun updateArticlesList(articlesList: List<ArticlesItem>) {
        this.articlesList = articlesList
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onClick(articlesItem: ArticlesItem)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

}