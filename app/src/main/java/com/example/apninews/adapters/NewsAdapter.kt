package com.example.apninews.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apninews.R
import com.example.apninews.models.Article

class NewsAdapter(private val listener: NewsClicked) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {


    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivArticleImage = itemView.findViewById<ImageView>(R.id.ivArticleImage)
        val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        val tvDescription = itemView.findViewById<TextView>(R.id.tvDescription)
        val tvSource = itemView.findViewById<TextView>(R.id.tvSource)
        val tvPublishedAt = itemView.findViewById<TextView>(R.id.tvPublishedAt)
    }

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article_view,parent,false)
        val viewHolder = NewsViewHolder(view)

        view.setOnClickListener {
            listener.onItemClicked(differ.currentList[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = differ.currentList[position]
        val context = holder.itemView.context
        holder.apply {
            Glide.with(context).load(article.urlToImage).into(ivArticleImage)
            tvTitle.text = article.title
            tvDescription.text = article.description
            tvPublishedAt.text = article.publishedAt
            tvSource.text  = article.source?.name
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    interface NewsClicked {
        fun onItemClicked(item: Article)
    }

}