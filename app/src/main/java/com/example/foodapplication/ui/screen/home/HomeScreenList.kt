package com.example.foodapplication.ui.screen.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.data.Resource
import com.example.core.domain.model.Article
import com.example.core.domain.model.Cooking
import com.example.foodapplication.R
import com.example.foodapplication.ui.common.UiState
import com.example.foodapplication.ui.components.ItemFoodsVertical
import com.example.foodapplication.ui.components.loading.SkeletonItemHorizontal
import com.example.foodapplication.ui.theme.Orange200
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreenListView (
    indicator: String = "",
    viewModel: HomeScreenViewModel = koinViewModel(),
    navigateToDetail: (String) -> Unit
) {
    // indicator to select display, whether cooking / articles

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
                            articles = article.data,
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
    modifier: Modifier = Modifier,
    cooking: Resource<List<Cooking>>,
    navigateToDetail: (String) -> Unit
) {
    Column {
        TopAppBar(
            backgroundColor = Orange200,
            contentPadding = PaddingValues(12.dp),
            elevation = 5.dp,
        ) {
            Text(
                text = stringResource(id = R.string.cooking_list),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
            )
        }
        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            contentPadding = PaddingValues(16.dp),
        ) {
            when (cooking) {
                is Resource.Loading -> {
                    items(6) {
                        SkeletonItemHorizontal(isLoading = true, modifier = modifier)
                    }
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
}

@Composable
fun HomeListViewArticle (
    modifier: Modifier = Modifier,
    articles: Resource<List<Article>>,
    navigateToDetail: (String) -> Unit
) {
    Column {
        TopAppBar(
            backgroundColor = Orange200,
            contentPadding = PaddingValues(12.dp),
            elevation = 5.dp,
        ) {
            Text(
                text = stringResource(id = R.string.article_list),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
            )
        }
        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            contentPadding = PaddingValues(16.dp),
        ) {
            when (articles) {
                is Resource.Loading -> {
                    items(6) {
                        SkeletonItemHorizontal(isLoading = true, modifier = modifier)
                    }
                }
                is Resource.Success -> {
                    items(articles.data!!) { article ->
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
}