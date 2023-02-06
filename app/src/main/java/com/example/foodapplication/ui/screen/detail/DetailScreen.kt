package com.example.foodapplication.ui.screen.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.example.foodapplication.ui.common.UiState
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailScreen (
    foodId: List<String>,
    viewModel: DetailViewModel = koinViewModel(),
    navigateBack: () -> Unit,
) {
    viewModel.uiState.collectAsState( initial = UiState.Loading).value.let {uiState ->
        when(uiState) {
            is UiState.Loading -> {
                viewModel.getFoodById(foodId)
            }
            is UiState.Success -> {
                val data = uiState.data

            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    thumb: String,
    title: String,
    basePoint: Int,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = thumb,
        contentDescription = "image $title",
        contentScale = ContentScale.Crop,
        modifier = modifier.fillMaxWidth()
    )
    
    AndroidView(
        factory = {
            ObservableScrollView(it).apply {

            }
        }
    )
}

@Preview
@Composable
fun DetailContentPreview() {
    MaterialTheme {
        DetailContent(
            thumb = "",
            title = "Hello",
            basePoint = 0,
            onBackClick = { /*TODO*/ }
        )
    }
}