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
import com.example.core.domain.model.Search

class SearchAdapter (private val onItemClick: (Search) -> Unit)
    : ListAdapter<Search, SearchAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Search> =
            object : DiffUtil.ItemCallback<Search>() {
                override fun areItemsTheSame(oldUser: Search, newUser: Search): Boolean {
                    return oldUser.title == newUser.title
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldUser: Search, newUser: Search): Boolean {
                    return oldUser == newUser
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSearch = getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListBinding.bind(itemView)
        fun bind(dataSearch: Search) {
            if (dataSearch != null){
                binding.composeView.setContent {
                    MaterialTheme {
                        ListItemFood(
                            urlToImage = dataSearch.thumb,
                            title = dataSearch.title,
                            difficulty = dataSearch.difficulty,
                            publishedAt = dataSearch.times,
                            onItemClick = { onItemClick(dataSearch) }
                        )
                    }
                }
            }
        }
    }
}

