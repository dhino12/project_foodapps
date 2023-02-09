package com.example.foodapplication.ui.screen.favorite

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.core.data.Resource
import com.example.core.domain.model.Cooking
import com.example.foodapplication.R
import com.example.foodapplication.ui.common.UiState
import com.example.foodapplication.ui.components.ItemFoodsHorizontal
import org.koin.androidx.compose.koinViewModel


@Composable
fun FavoriteScreen(
    viewModel: FavoriteFoodViewModel = koinViewModel(),
    toDetail: (String) -> Unit
) {
    viewModel.uiStateFavorite.collectAsState(initial = UiState.Loading)
        .value.let {cookingFavorite ->
            when(cookingFavorite) {
                is UiState.Loading -> {
                    viewModel.getAllFavorite()
                }
                is UiState.Success -> {
                    FavoriteContent(
                        favorites = cookingFavorite.data,
                        toDetail = toDetail
                    )
                }
                is UiState.Error -> {}
            }
        }
}

@Composable
fun FavoriteContent(
    favorites: List<Cooking>,
    toDetail: (String) -> Unit
) {
    Column (verticalArrangement = Arrangement.Center) {
        TopAppBar(
            backgroundColor = Color(ContextCompat.getColor(LocalContext.current, R.color.orange)),
            contentPadding = PaddingValues(12.dp),
            elevation = 5.dp,
        ) {
            Text(
                text = stringResource(id = R.string.title_favorite),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
            )
        }

        if (favorites.isEmpty()) {
            ConstraintLayout (modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
                val (textInfo) = createRefs()
                Text(
                    text = stringResource(R.string.message_not_favorite_found),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.constrainAs(textInfo) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                )
            }
            return@Column
        }

        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            contentPadding = PaddingValues(25.dp),
            horizontalArrangement = Arrangement.spacedBy(26.dp),
            verticalArrangement = Arrangement.spacedBy(26.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(favorites) { cookingFav ->
                ItemFoodsHorizontal(
                    title= cookingFav.title,
                    urlToImage = cookingFav.thumb,
                    key = cookingFav.title,
                    publishedAt = cookingFav.times,
                    tags = cookingFav.tags,
                    difficulty = cookingFav.difficulty,
                    onItemClick = toDetail
                )
            }
        }
    }
}