package com.example.core.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core.R
import com.example.core.databinding.ItemIngredientsBinding

class IngredientsAdapter
    : ListAdapter<String, IngredientsAdapter.IngredientsViewHolder>(DIFF_CALLBACK) {
    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<String> =
            object : DiffUtil.ItemCallback<String>() {
                override fun areItemsTheSame(oldUser: String, newUser: String): Boolean {
                    return oldUser == newUser
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldUser: String, newUser: String): Boolean {
                    return oldUser == newUser
                }
            }
    }
    class IngredientsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemIngredientsBinding.bind(itemView)
        fun bind(ingredients: String) {
            binding.composeView.setContent {
                IngredientsAdapter(ingredients)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ingredients, parent, false)
        return IngredientsViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

@Composable
fun IngredientsAdapter(
    textDesc: String = "none"
) {
    Card(
        shape = RoundedCornerShape(4.dp),
        elevation = 4.dp,
        border = BorderStroke(1.dp, Color(255, 190, 160, 255)),
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text(
            text = textDesc,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
            maxLines = 3,
            fontSize = 13.sp,
            modifier = Modifier
                .padding(horizontal = 6.dp, vertical = 4.dp)
        )
    }
}

@Preview
@Composable
fun IngredientsAdapterPrev() {
    MaterialTheme {
        IngredientsAdapter(
            "Hello World"
        )
    }
}