package com.example.foodapplication.ui.screen.home

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core.data.Resource
import com.example.core.domain.model.Article
import com.example.core.domain.model.Cooking
import com.example.foodapplication.ui.common.UiState
import com.example.foodapplication.ui.components.ItemFoodsVertical
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreenListView (
    modifier: Modifier = Modifier,
    indicator: String = "",
    viewModel: HomeScreenViewModel = koinViewModel(),
    navigateToDetail: (String) -> Unit
) {
    // indicator to select display, whether cooking / articles

    Log.e("indicator", indicator)
    if (indicator == "cooking") {
        viewModel.uiStateCooking.collectAsState(initial = UiState.Loading)
            .value.let {cooking ->
                when (cooking) {
                    is UiState.Loading -> viewModel.getAllItemRecipe()
                    is UiState.Success -> {
                        Log.e("dataList", cooking.data.toString())
                        HomeListView(
                            cooking = cooking.data,
                            navigateToDetail = navigateToDetail
                        )
                    }
                    is UiState.Error -> {}
                }
            }
    }
    if (indicator == "article") {
        viewModel.uiStateArticle.collectAsState(initial = UiState.Loading)
            .value.let {article ->
                when (article) {
                    is UiState.Loading -> viewModel.getAllItemRecipe()
                    is UiState.Success -> {
                        Log.e("dataList", article.data.toString())
                        HomeListViewArticle(
                            cooking = article.data,
                            navigateToDetail = navigateToDetail
                        )
                    }
                    is UiState.Error -> {}
                }
            }
    }
}

@Composable
fun HomeListView (
    cooking: Resource<List<Cooking>>,
    navigateToDetail: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(16.dp),
    ) {
        when (cooking) {
            is Resource.Loading -> {

            }
            is Resource.Success -> {
                items(cooking.data!!) { cookingData ->
                    ItemFoodsVertical(
                        thumb = cookingData.thumb,
                        title = cookingData.title,
                        difficulty = cookingData.difficulty,
                        time = cookingData.times,
                        tags = cookingData.tags,
                        OnItemClick = { navigateToDetail("${cookingData.cookingID!!}&${cookingData.title!!}") },
                    )
                }
            }
            is Resource.Error -> {}
        }
    }
}

@Composable
fun HomeListViewArticle (
    cooking: Resource<List<Article>>,
    navigateToDetail: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(16.dp),
    ) {
        when (cooking) {
            is Resource.Loading -> {

            }
            is Resource.Success -> {
                items(cooking.data!!) { article ->
                    ItemFoodsVertical(
                        thumb = article.thumb,
                        title = article.title,
                        time = article.datePublished,
                        tags = article.tags,
                        OnItemClick = {
                            navigateToDetail("${article.key!!.replace("/", "&")}&${article.title}")
                        },
                    )
                }
            }
            is Resource.Error -> {}
        }
    }
}