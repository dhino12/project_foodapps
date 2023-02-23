package com.example.foodapplication.ui.screen.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.foodapplication.ui.components.ArticleComposable
import com.example.foodapplication.ui.components.CookingComposable
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

    var cookings: Resource<List<Cooking>>? = null
    var articles: Resource<List<Article>>? = null

    when(viewModelCooking) {
        is UiState.Loading -> {
            viewModel.getAllItemRecipe()
        }
        is UiState.Success -> {
            cookings = viewModelCooking.data
        }
        is UiState.Error -> {}
    }

    when(viewModelArticle) {
        is UiState.Loading -> {
            viewModel.getAllItemArticle()
        }
        is UiState.Success -> {
            articles = viewModelArticle.data
        }
        is UiState.Error -> {}
    }

    HomeContent(
        cooking = cookings,
        articles = articles,
        modifier = modifier,
        navigateToDetailCooking = navigateToDetailCooking,
        navigateToArticleList = navigateToArticleList,
        navigateToCookingList = navigateToCookingList,
        navigateToSearch = navigateToSearch,
        navigateToDetailArticle = {},
    )
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
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {
        val (
            textWelcome, textDesc,
            randomButton, searchButton,
            listArticleButton, listCookingButton,
            listArticle, listCooking, box1, imgBackground ) = createRefs()

        Image(
            painter = painterResource(R.drawable.background_home),
            contentDescription = "background_wave",
            contentScale = ContentScale.FillHeight,
            modifier = modifier.constrainAs(imgBackground) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }
        )

        Text(
            text = stringResource(R.string.welcome2),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .padding(20.dp)
                .constrainAs(textWelcome){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )
        Text(
            text = stringResource(R.string.welcome),
            fontSize = 13.sp,
            fontWeight = FontWeight(400),
            color = Color.DarkGray,
            modifier = Modifier
                .padding(bottom = 10.dp, start = 20.dp)
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
                .height(240.dp)
                .constrainAs(listArticle) {
                    top.linkTo(listArticleButton.bottom)
                    start.linkTo(listArticleButton.start)
                    end.linkTo(parent.end)
                }
                .testTag("Articles"),
            navigateToDetailArticle = navigateToDetailArticle
        )
        IconButton(
            onClick = { navigateToCookingList() },
            modifier = modifier
                .padding(top = 220.dp, bottom = 5.dp)
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
                .height(280.dp)
                .constrainAs(listCooking) {
                    top.linkTo(listCookingButton.bottom)
                    start.linkTo(listCookingButton.start)
                    end.linkTo(parent.end)
                }
                .testTag("cooking"),
            navigateToDetailCooking = navigateToDetailCooking
        )
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
            navigateToSearch = {},
        )
    }
}