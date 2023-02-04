package com.example.core.ui

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.compose.AsyncImage
import com.bumptech.glide.Glide
import com.example.core.R
import com.example.core.databinding.ItemListBinding
import com.example.core.domain.model.Search
import com.example.core.domain.model.Cooking

class ListItemAdapter (private val onItemClick: (Cooking) -> Unit)
    : ListAdapter<Cooking, ListItemAdapter.ViewHolder> (DIFF_CALLBACK) {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataCooking = getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListBinding.bind(itemView)
        fun bind(dataCooking: Cooking) {
            if (dataCooking != null){
                Log.e("fav", dataCooking.toString())
                binding.composeView.setContent {
                    MaterialTheme {
                        ListItemFood(
                            urlToImage = dataCooking.thumb,
                            title = dataCooking.title,
                            publishedAt = dataCooking.times,
                            difficulty = dataCooking.difficulty,
                            tags = dataCooking.tags,
                            onItemClick = { onItemClick(dataCooking) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ListItemFood(
    urlToImage: String? = "",
    title: String? = null,
    publishedAt: String? = null,
    difficulty: String? = null,
    tags: String? = null,
    onItemClick: () -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .padding(12.dp)
            .clickable { onItemClick() }
    ) {
        val (image, titleText, publishedText, difficultyText, tagText) = createRefs()

        Card(
            shape = MaterialTheme.shapes.medium,
            elevation = 2.dp,
            border = BorderStroke(1.dp, Color.Black),
            modifier = Modifier
                .size(120.dp)
                .constrainAs(image) {
                    top.linkTo(parent.top)
                }
        ) {
            AsyncImage(
                model = urlToImage,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
        Text(
            text = if (title.isNullOrEmpty()) "" else title,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
            maxLines = 3,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(titleText) {
                start.linkTo(image.end, margin = 16.dp)
                top.linkTo(parent.top, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                width = Dimension.fillToConstraints
            }
        )
        Text(
            text = if (publishedAt.isNullOrEmpty()) "" else publishedAt,
            fontSize = 12.sp,
            modifier = Modifier.constrainAs(publishedText) {
                top.linkTo(titleText.bottom, margin = 16.dp)
                start.linkTo(titleText.start)
            }
        )
        Text(
            text = if (difficulty.isNullOrEmpty()) "" else difficulty,
            fontSize = 13.sp,
            modifier = Modifier.constrainAs(difficultyText) {
                top.linkTo(publishedText.top)
                start.linkTo(publishedText.end, margin = 12.dp)
                end.linkTo(parent.end, margin = 16.dp)
            }
        )
        if (!tags.isNullOrEmpty()) {
            Box (
                modifier = Modifier
                    .constrainAs(tagText) {
                        top.linkTo(publishedText.bottom)
                        start.linkTo(publishedText.start)
                    }
                    .padding(top = 10.dp)
                    .background(Color(230, 73, 0))
                    .padding(vertical = 2.dp, horizontal = 8.dp)
            ) {
                Text(
                    text = tags,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ListItemPreview() {
    MaterialTheme {
        ListItemFood(
            urlToImage = "",
            title = "Nasi Goreng",
            publishedAt = "2022-01-01",
            difficulty = "Sulit",
            tags = "tata",
            {}
        )
    }
}