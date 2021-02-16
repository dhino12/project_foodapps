package com.example.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.R
import com.example.core.databinding.ItemListBinding
import com.example.core.domain.model.Cooking

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private val favoriteList = ArrayList<Cooking>()
    var onItemClick: ((Cooking) -> Unit)? = null

    fun setData(newListContent: List<Cooking>?) {
        if (newListContent == null) return
        favoriteList.clear()
        favoriteList.addAll(newListContent)
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListBinding.bind(itemView)
        fun bind(data: Cooking) {
            with(binding) {
                Glide.with(itemView.context)
                        .load(data.thumb)
                        .into(imgList)
                tvTitleList.text = data.title
                tvTimesList.text = data.times
                tvDifficultyList.text = data.difficulty
                tvPortionList.text = data.servings

                tvTimesList.visibility = View.VISIBLE
                tvDifficultyList.visibility = View.VISIBLE
                tvPortionList.visibility = View.VISIBLE
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(favoriteList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(favoriteList[position])
    }

    override fun getItemCount(): Int = favoriteList.size
}