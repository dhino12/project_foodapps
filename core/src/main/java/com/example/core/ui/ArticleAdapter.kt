package com.example.core.ui

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.R
import com.example.core.databinding.ItemArticleBinding
import com.example.core.databinding.ItemRecentBinding
import com.example.core.domain.model.Article
import com.example.core.domain.model.Cooking

class ArticleAdapter (private val onItemClick: (Article) -> Unit)
    : ListAdapter<Article, ArticleAdapter.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Article> =
            object : DiffUtil.ItemCallback<Article>() {
                override fun areItemsTheSame(oldUser: Article, newUser: Article): Boolean {
                    return oldUser.title == newUser.title
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldUser: Article, newUser: Article): Boolean {
                    return oldUser == newUser
                }
            }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRecentBinding.bind(itemView)
        fun bind(data: Article) {

            binding.composeView.setContent {
                MaterialTheme {
                    ItemCardVertical(
                        thumb = data.thumb,
                        title = data.title,
                        time = data.datePublished,
                        tags = data.tags,
                        OnItemClick = { onItemClick(data) }
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recent, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}