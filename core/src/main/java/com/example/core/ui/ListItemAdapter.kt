package com.example.core.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.R
import com.example.core.databinding.ItemListBinding
import com.example.core.domain.model.Article
import com.example.core.domain.model.Cooking

class ListItemAdapter : RecyclerView.Adapter<ListItemAdapter.ViewHolder>() {

    private var listDataArticle = ArrayList<Article>()
    private var listDataCooking = ArrayList<Cooking>()
    var onItemClickArticle: ((Article) -> Unit)? = null
    var onItemClickCooking: ((Cooking) -> Unit)? = null

    fun setData(
            newListDataArticle: List<Article>? = null,
            newListDataCooking: List<Cooking>? = null
    ) {
        if (newListDataArticle == null) {
            listDataCooking.clear()
            if (newListDataCooking != null) {
                listDataCooking.addAll(newListDataCooking)
            }
            notifyDataSetChanged()
        } else {
            listDataArticle.clear()
            listDataArticle.addAll(newListDataArticle)
            Log.e("err_total_list_dataArticle: ", listDataArticle.size.toString())
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (listDataArticle.isNullOrEmpty()) {
            holder.bind(dataCooking = listDataCooking[position])
        } else {
            holder.bind(dataArticle = listDataArticle[position])
        }
    }

    override fun getItemCount(): Int {
        return if (listDataArticle.isNullOrEmpty()) {
            listDataCooking.size
        } else {
            listDataArticle.size
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListBinding.bind(itemView)
        fun bind(dataArticle: Article? = null, dataCooking: Cooking? = null) {
            if (dataArticle != null) {
                with(binding) {
                    Glide.with(itemView.context)
                            .load(dataArticle.thumb)
                            .into(imgList)
                    tvTitleList.text = dataArticle.title
                    tagsList.text = dataArticle.tags
                    tagsList.visibility = View.VISIBLE
                }
                Log.e("err_total_list_dataArticle: ", listDataArticle.size.toString())
            } else {
                with(binding) {
                    Glide.with(itemView.context)
                            .load(dataCooking?.thumb)
                            .into(imgList)
                    tvTitleList.text = dataCooking?.title
                    tvTimesList.text = dataCooking?.times
                    tvDifficultyList.text = dataCooking?.difficulty
                    tvPortionList.text = dataCooking?.servings
                    tvTimesList.visibility = View.VISIBLE
                    tvDifficultyList.visibility = View.VISIBLE
                    tvPortionList.visibility = View.VISIBLE
                    Log.e("err_total_list_dataCooking: ", listDataCooking.size.toString())
                }
            }
        }

        init {
            if (onItemClickArticle != null) {
                binding.root.setOnClickListener {
                    onItemClickArticle?.invoke(listDataArticle[adapterPosition])
                }
            } else {
                binding.root.setOnClickListener {
                    onItemClickCooking?.invoke(listDataCooking[adapterPosition])
                }
            }
        }
    }

}