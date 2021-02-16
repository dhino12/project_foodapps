package com.example.core.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.R
import com.example.core.databinding.ItemCategoryBinding
import com.example.core.domain.model.Category

class ListCategoryAdapter : RecyclerView.Adapter<ListCategoryAdapter.CategoryViewHolder>() {

    private val listData = ArrayList<Category>()
    var onClick: ((Category, View) -> Unit)? = null

    fun setData(newListData: List<Category>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemCategory = ItemCategoryBinding.bind(itemView)
        fun bind(data: Category) {
            with(itemCategory) {
                tvTitleCategory.text = data.category
                Log.e("errorListAdapter", data.category.toString())
            }


        }

        init {
            itemCategory.root.setOnClickListener {
                onClick?.invoke(listData[adapterPosition], itemView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size
}