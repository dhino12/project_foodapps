package com.example.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.R
import com.example.core.databinding.ItemListBinding
import com.example.core.domain.model.Cooking

class ContentCategoryAdapter : RecyclerView.Adapter<ContentCategoryAdapter.ContentViewHolder>() {

    private val contentCategory = ArrayList<Cooking>()
    var onItemClickContent: ((Cooking) -> Unit)? = null

    fun setData(newListContent: List<Cooking>?) {
        if (newListContent == null) return
        contentCategory.clear()
        contentCategory.addAll(newListContent)
        notifyDataSetChanged()
    }

    inner class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListBinding.bind(itemView)
        fun bind(data: Cooking) {
            with(binding) {
                tvTimesList.visibility = View.VISIBLE
                tvDifficultyList.visibility = View.VISIBLE
                tvPortionList.visibility = View.VISIBLE

                Glide.with(itemView.context)
                        .load(data.thumb)
                        .into(imgList)
                tvTitleList.text = data.title
                tvTimesList.text = data.times
                tvDifficultyList.text = data.difficulty
                tvPortionList.text = data.servings
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClickContent?.invoke(contentCategory[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ContentViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(contentCategory[position])
    }

    override fun getItemCount(): Int = contentCategory.size
}