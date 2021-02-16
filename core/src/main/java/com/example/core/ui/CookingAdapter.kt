package com.example.core.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.R
import com.example.core.databinding.ItemRecentBinding
import com.example.core.domain.model.Cooking

class CookingAdapter : RecyclerView.Adapter<CookingAdapter.ViewHolder>() {

    private var listData = ArrayList<Cooking>()
    var onItemClick: ((Cooking) -> Unit)? = null

    fun setData(newListData: List<Cooking>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRecentBinding.bind(itemView)
        fun bind(data: Cooking) {
            with(binding) {
                Glide.with(itemView.context)
                        .load(data.thumb)
                        .into(imgFood)
                tvTitleFood.text = data.title
                times.text = data.times
                difficulty.text = data.difficulty
                portion.text = data.servings
                Log.e("cookingTitle", data.title.toString())

            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recent, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size
}