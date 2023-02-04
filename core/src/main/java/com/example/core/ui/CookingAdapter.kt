package com.example.core.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.compose.AsyncImage
import com.example.core.R
import com.example.core.databinding.ItemRecentBinding
import com.example.core.domain.model.Cooking

class CookingAdapter (private val onItemClick: (Cooking) -> Unit)
    : ListAdapter<Cooking, CookingAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Cooking> =
            object : DiffUtil.ItemCallback<Cooking>() {
                override fun areItemsTheSame(oldUser: Cooking, newUser: Cooking): Boolean {
                    return oldUser.title == newUser.title
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldUser: Cooking, newUser: Cooking): Boolean {
                    return oldUser == newUser
                }
            }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRecentBinding.bind(itemView)
        fun bind(data: Cooking) {
            binding.composeView.setContent {
                MaterialTheme {
                    ItemCardVertical(
                        thumb = data.thumb,
                        title = data.title,
                        time = data.times,
                        difficulty = data.difficulty,
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

@Composable
fun ItemCardVertical(
    modifier: Modifier = Modifier,
    thumb: String? = "",
    title: String? = "untitled",
    time: String? = "00:00",
    difficulty: String? = "none",
    tags: String? = "none",
    OnItemClick: () -> Unit,
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = 3.dp,
        modifier = modifier
            .padding(10.dp)
            .clickable { OnItemClick() }
    ) {
        Box {
            Text(
                text = tags.toString(),
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                color = Color.White,
                maxLines = 3,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier
                    .padding(start = 12.dp, top = 10.dp)
                    .background(Color(230, 73, 0))
                    .padding(horizontal = 5.dp, vertical = 2.dp)
            )
        }

        Column (
            modifier = modifier.fillMaxWidth(),
        ) {
            AsyncImage(
                model = thumb,
                contentDescription = "food $title",
                contentScale = ContentScale.Crop,
                modifier = modifier.widthIn(180.dp).height(120.dp)
            )
            Text(
                text = title.toString(),
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                color = Color(230, 73, 0),
                maxLines = 3,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier.padding(start = 12.dp, top = 10.dp)
            )
            Row (
                modifier = modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = time.toString(),
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                    maxLines = 3,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = modifier.width(10.dp))

                if (!difficulty.isNullOrEmpty()) {
                    Text(
                        text = difficulty.toString(),
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start,
                        maxLines = 3,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview (showBackground = true)
@Composable
fun ItemCardVerticalPreview() {
    MaterialTheme {
        ItemCardVertical(
            thumb = "",
            title = "Ada Aja",
            time = "121",
            difficulty = "sulit",
            tags = "aja",
            OnItemClick = {}
        )
    }
}