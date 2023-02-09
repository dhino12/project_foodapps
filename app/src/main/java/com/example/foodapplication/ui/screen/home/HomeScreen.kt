package com.example.foodapplication.ui.screen.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.core.data.Resource
import com.example.core.domain.model.Article
import com.example.core.domain.model.Cooking
import com.example.foodapplication.R
import com.example.foodapplication.ui.common.UiState
import com.example.foodapplication.ui.components.ItemFoodsVertical
import com.example.foodapplication.ui.components.loading.SkeletonItemVertical
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = getViewModel(),
    navigateToDetailCooking: (String) -> Unit,
    navigateToDetailArticle: (String) -> Unit,
    navigateToCookingList: () -> Unit,
    navigateToArticleList: () -> Unit,
    navigateToSearch: () -> Unit
) {
    val viewModelCooking = viewModel.uiStateCooking.collectAsState(
        initial = UiState.Loading
    ).value

    val viewModelArticle = viewModel.uiStateArticle.collectAsState(
        initial = UiState.Loading
    ).value

    when(viewModelCooking) {
        is UiState.Loading -> {
            viewModel.getAllItemRecipe()
        }
        is UiState.Success -> {
            HomeContent(
                cooking = viewModelCooking.data,
                modifier = modifier,
                navigateToDetailCooking = navigateToDetailCooking,
                navigateToArticleList = navigateToArticleList,
                navigateToCookingList = navigateToCookingList,
                navigateToSearch = navigateToSearch,
                navigateToDetailArticle = {},
            )
        }
        is UiState.Error -> {}
    }

    when(viewModelArticle) {
        is UiState.Loading -> {
            viewModel.getAllItemArticle()
        }
        is UiState.Success -> {
            HomeContent(
                articles = viewModelArticle.data,
                modifier = modifier,
                navigateToDetailArticle = navigateToDetailArticle,
                navigateToCookingList = navigateToCookingList,
                navigateToArticleList = navigateToArticleList,
                navigateToSearch = navigateToSearch,
                navigateToDetailCooking = {},
            )
        }
        is UiState.Error -> {}
    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    cooking: Resource<List<Cooking>>? = null,
    articles: Resource<List<Article>>? = null,
    navigateToDetailCooking: (String) -> Unit,
    navigateToDetailArticle: (String) -> Unit,
    navigateToCookingList: () -> Unit,
    navigateToArticleList: () -> Unit,
    navigateToSearch: () -> Unit,
) {
    Image(
        painter = painterResource(R.drawable.background_home),
        contentDescription = "background_wave",
        contentScale = ContentScale.FillHeight
    )
    ConstraintLayout(
        modifier = modifier
            .fillMaxHeight()
            .padding(12.dp)
    ) {
        val (
            textWelcome, textDesc,
            randomButton, searchButton,
            listArticleButton, listCookingButton,
            listArticle, listCooking ) = createRefs()

        Text(
            text = stringResource(R.string.welcome2),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .constrainAs(textWelcome){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )
        Text(
            text = stringResource(R.string.welcome),
            fontSize = 13.sp,
            color = Color.DarkGray,
            modifier = Modifier
                .padding(vertical = 10.dp)
                .constrainAs(textDesc) {
                    top.linkTo(textWelcome.bottom)
                    start.linkTo(textWelcome.start)
                }
        )
        IconButton (
            onClick = {},
            modifier = modifier
                .padding(horizontal = 10.dp)
                .constrainAs(randomButton) {
                    top.linkTo(textDesc.top)
                    start.linkTo(textDesc.end)
                }) {
            Icon (
                imageVector = Icons.Filled.Refresh,
                contentDescription = "random button",
            )
        }

        IconButton (
            onClick = {navigateToSearch()},
            modifier = modifier
                .constrainAs(searchButton) {
                    top.linkTo(randomButton.top)
                    start.linkTo(randomButton.end)
                    end.linkTo(parent.end)
                }) {
            Icon (
                imageVector = Icons.Filled.Search,
                contentDescription = "search button",
            )
        }

        IconButton(
            modifier = modifier
                .padding(vertical = 10.dp)
                .constrainAs(listArticleButton) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(textDesc.bottom)
                },
            onClick = { navigateToArticleList()},
        ) {
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()

            ) {
                Text(
                    text = "Article",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(
                        ContextCompat
                            .getColor(LocalContext.current, R.color.orange)),
                )
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = "detail",
                    tint = Color(
                        ContextCompat
                            .getColor(LocalContext.current, R.color.orange)),
                )
            }
        }

        ArticleComposable(
            articles = articles,
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
                .constrainAs(listArticle) {
                    top.linkTo(listArticleButton.bottom)
                    start.linkTo(listArticleButton.start)
                    end.linkTo(parent.end)
                }
                .testTag("Articles"),
            articleComposable = { lazyModifier ->
                LazyHorizontalGrid(
                    rows = GridCells.Adaptive(160.dp),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = lazyModifier
                ) {
                    if (articles?.data != null) {
                        items(articles.data!!) { article ->
                            Log.e("data", article.title.toString())
                            ItemFoodsVertical(
                                thumb = article.thumb,
                                title = article.title,
                                time = article.datePublished,
                                tags = article.tags,
                                OnItemClick = { navigateToDetailArticle(
                                    "${article.key!!.replace("/", "&")}&${article.title}"
                                ) }
                            )
                        }
                    }
                }
            }
        )
        IconButton(
            onClick = { navigateToCookingList() },
            modifier = modifier
                .padding(top = 200.dp, bottom = 5.dp)
                .constrainAs(listCookingButton) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(listArticleButton.bottom)
                }
        ) {
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Cooking",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(
                        ContextCompat
                            .getColor(LocalContext.current, R.color.orange)),
                )
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = "detail",
                    tint = Color(
                        ContextCompat
                            .getColor(LocalContext.current, R.color.orange)),
                )
            }
        }

        CookingComposable(
            cooking = cooking,
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(0.38f)
                .constrainAs(listCooking) {
                    top.linkTo(listCookingButton.bottom)
                    start.linkTo(listCookingButton.start)
                    end.linkTo(parent.end)
                }
                .testTag("cooking"),
            cookingComposable = { modifierLazy ->
                LazyHorizontalGrid(
                    rows = GridCells.Adaptive(160.dp),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = modifierLazy
                ) {
                    if (cooking?.data != null) {
                        items(cooking.data!!) { cookingData ->
                            ItemFoodsVertical(
                                thumb = cookingData.thumb,
                                title = cookingData.title,
                                difficulty = cookingData.difficulty,
                                time = cookingData.times,
                                tags = cookingData.tags,
                                OnItemClick = { navigateToDetailCooking("${cookingData.cookingID!!}&${cookingData.title!!}") },
                            )
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun ArticleComposable(
    modifier: Modifier = Modifier,
    articles: Resource<List<Article>>? = null,
    articleComposable: @Composable (modifier: Modifier) -> Unit,
) {
    /** TODO create a modifier so that it can be used on components when loading,
     * as well as full data so it's not complicated
     */
    when(articles) {
        is Resource.Loading -> {
            SkeletonItemVertical(isLoading = true, modifier = modifier)
        }
        is Resource.Success -> {
            articleComposable(modifier = modifier)
            Log.d("dataArticle", articles.data.toString())
        }
        is Resource.Error -> {}
        else -> {}
    }
}

@Composable
fun CookingComposable(
    modifier: Modifier = Modifier,
    cooking: Resource<List<Cooking>>? = null,
    cookingComposable: @Composable (modifier: Modifier) -> Unit
) {
    /** TODO create a modifier so that it can be used on components when loading,
     * as well as full data so it's not complicated
     */
    when(cooking) {
        is Resource.Loading -> {
            SkeletonItemVertical(isLoading = true, modifier = modifier)
        }
        is Resource.Success -> {
            cookingComposable(modifier = modifier)
        }
        is Resource.Error -> {}
        else -> {}
    }
}

@Preview (showBackground = true)
@Composable
fun HomeContentPreview() {
    MaterialTheme {
        HomeContent(
            navigateToDetailCooking = {},
            navigateToDetailArticle = {},
            navigateToCookingList = {},
            navigateToArticleList = {},
            navigateToSearch = {}
        )
    }
}