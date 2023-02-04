package com.example.core.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core.R
import com.example.core.databinding.ItemListBinding
import com.example.core.domain.model.Article

class ListArticleAdapter (private val onItemClick: (Article) -> Unit)
    : ListAdapter<Article, ListArticleAdapter.ViewHolder>(DIFF_CALLBACK) {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListArticleAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataArticle = getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListBinding.bind(itemView)
        fun bind(dataArticle: Article) {
            if (dataArticle != null){
                binding.composeView.setContent {
                    MaterialTheme {
                        ListItemFood(
                            urlToImage = dataArticle.thumb,
                            title = dataArticle.title,
                            publishedAt = dataArticle.datePublished,
                            tags = dataArticle.tags,
                            difficulty = null,
                            onItemClick = { onItemClick(dataArticle) }
                        )
                    }
                }
            }
        }
    }
}

