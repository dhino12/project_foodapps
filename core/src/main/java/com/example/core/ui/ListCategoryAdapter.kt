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
import com.example.core.R
import com.example.core.databinding.ItemCategoryBinding
import com.example.core.domain.model.Category

class ListCategoryAdapter (private val onItemClick: (Category, View) -> Unit)
    : ListAdapter<Category, ListCategoryAdapter.CategoryViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Category> =
            object : DiffUtil.ItemCallback<Category>() {
                override fun areItemsTheSame(oldUser: Category, newUser: Category): Boolean {
                    return oldUser.category == newUser.category
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldUser: Category, newUser: Category): Boolean {
                    return oldUser == newUser
                }
            }
    }

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemCategory = ItemCategoryBinding.bind(itemView)
        fun bind(data: Category) {
            itemCategory.composeView.setContent {
                MaterialTheme {
                    ItemCardVertical(
                        title = data.category.toString(),
                        OnItemClick = { onItemClick(data, itemView) }
                    )
                }
            }
            Log.e("errorListAdapter", data.category.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}