package com.example.foodapplication.ui.components.loading

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun SkeletonCardTitle(
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    Log.e("cardTitle", "jalan")
    if (!isLoading) return
    Card (
        shape = RoundedCornerShape(5.dp),
        modifier = modifier.fillMaxWidth(),
        elevation = 5.dp,
    ) {
        Row (modifier = modifier
            .padding(vertical = 10.dp, horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(
                modifier = modifier
                    .fillMaxWidth(0.8f)
                    .height(40.dp)
                    .shimmerEffect()
                    .clip(RoundedCornerShape(5.dp))
            )
            Spacer(
                modifier = modifier
                    .width(20.dp)
                    .height(40.dp)
            )
            Box(modifier = modifier
                .width(20.dp)
                .height(40.dp)
                .shimmerEffect()
                .clip(RoundedCornerShape(5.dp))
            )
        }
    }
}

@Preview
@Composable
private fun SkeletonCardTitlePrev() {
    MaterialTheme {
        SkeletonCardTitle(isLoading = true)
    }
}