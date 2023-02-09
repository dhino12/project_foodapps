package com.example.foodapplication.ui.screen.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.data.Resource
import com.example.core.domain.model.Cooking
import com.example.foodapplication.ui.common.UiState
import com.example.foodapplication.ui.components.ItemFoodsHorizontal
import com.example.foodapplication.ui.components.loading.SkeletonItemHorizontal
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListRecipeByCategory(
    category: String,
    viewModel: ContentCategoryViewModel = koinViewModel(),
    onItemToDetail: (String) -> Unit
) {
    viewModel.uiStateCooking.collectAsState(initial = UiState.Loading).value.let {
        cooking -> when(cooking) {
            is UiState.Loading -> {
                viewModel.getAllCookingByCategory(category)
            }
            is UiState.Success -> {
                when (cooking.data) {
                    is Resource.Loading -> {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(260.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            items(5) {
                                SkeletonItemHorizontal(isLoading = true)
                            }
                        }
                    }
                    is Resource.Success -> {
                        ContentRecipeByCategory(
                            cooking = cooking.data.data!!,
                            onItemToDetail = onItemToDetail
                        )
                    }
                    is Resource.Error -> {}
                }
            }
            is UiState.Error -> {

            }
        }
    }
}

@Composable
fun ContentRecipeByCategory(
    cooking: List<Cooking>,
    onItemToDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column (
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(260.dp),
            contentPadding = PaddingValues(15.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(cooking) {cookingData ->
                ItemFoodsHorizontal(
                    urlToImage = cookingData.thumb,
                    title = cookingData.title,
                    difficulty = cookingData.difficulty,
                    publishedAt = cookingData.times,
                    tags = cookingData.tags,
                    key = cookingData.cookingID,
                    onItemClick = onItemToDetail
                )
            }
        }
    }
}

@Preview (showBackground = true)
@Composable
fun ContentRecipeByCategoryPrev() {
    MaterialTheme {
        ContentRecipeByCategory(
            cooking = listOf(
                Cooking(title = "Test1"),
                Cooking(title = "Test2"),
                Cooking(title = "Test3"),
            ),
            onItemToDetail = {}
        )
    }
}