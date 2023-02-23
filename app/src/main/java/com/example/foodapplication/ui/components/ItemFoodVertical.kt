package com.example.foodapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.example.foodapplication.R


@Composable
fun ItemFoodsVertical(
    modifier: Modifier = Modifier,
    thumb: String? = "",
    title: String? = "untitled",
    time: String? = null,
    difficulty: String? = null,
    tags: String? = null,
    OnItemClick: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 3.dp,
        modifier = modifier
            .widthIn(max = 250.dp)
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

        Column{
            AsyncImage(
                model = thumb,
                contentDescription = "food $title",
                contentScale = ContentScale.Crop,
                modifier = modifier.fillMaxWidth().height(120.dp)
            )
            Text(
                text = title.toString(),
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                color = Color(ContextCompat.getColor(LocalContext.current, R.color.orange)),
                maxLines = 2,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier.padding(start = 12.dp, top = 10.dp, bottom = 12.dp, end = 12.dp)
            )
            Row (
                modifier = modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ){
                Text(
                    text = time ?: "",
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                    maxLines = 3,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = modifier.width(10.dp))

                Text(
                    text = difficulty ?: "",
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

@Preview(showBackground = true)
@Composable
fun ItemCardVerticalPreview() {
    MaterialTheme {
        ItemFoodsVertical(
            thumb = "",
            title = "Ada Ajaaaaaaaaaaa",
            time = "121",
            difficulty = "sulit",
            tags = "aja",
            OnItemClick = {}
        )
    }
}