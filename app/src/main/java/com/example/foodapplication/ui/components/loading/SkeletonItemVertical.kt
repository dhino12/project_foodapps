package com.example.foodapplication.ui.components.loading

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.domain.model.Cooking
import kotlinx.coroutines.delay

@Composable
fun SkeletonItemVertical(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
) {
    if (!isLoading) return
    Card (
        shape = RoundedCornerShape(12.dp),
        elevation = 3.dp,
        modifier = modifier
            .padding(10.dp)
            .widthIn(max = 350.dp)
            .heightIn(max = 250.dp)
    ) {
        Column(modifier = modifier.fillMaxSize()) {
            Box(modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
                .height(120.dp)
                .clip(RoundedCornerShape(12.dp))
                .shimmerEffect()
            )
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .shimmerEffect()
            )
            Row(
                modifier = modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier
                    .width(76.dp)
                    .height(20.dp)
                    .shimmerEffect()
                )
                Spacer(modifier = Modifier
                    .width(18.dp)
                )
                Spacer(modifier = Modifier
                    .width(26.dp)
                    .height(20.dp)
                    .shimmerEffect()
                )
            }
        }
    }
}

@Preview (showBackground = true)
@Composable
fun SkeletonItemVerticalPrev() {
    MaterialTheme {
        var isLoading by remember {
            mutableStateOf(true)
        }
        LaunchedEffect(key1 = true) {
            delay(10000)
            isLoading = false
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(20) {
                SkeletonItemVertical(
                    isLoading = isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}