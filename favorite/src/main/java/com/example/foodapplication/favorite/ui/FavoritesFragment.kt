package com.example.foodapplication.favorite.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.domain.model.Cooking
import com.example.core.ui.ListItemAdapter
import com.example.favorite.R
import com.example.foodapplication.databinding.FragmentListItemBinding
import com.example.foodapplication.ui.common.UiState
import com.example.foodapplication.ui.components.ItemFoodsHorizontal
import com.example.foodapplication.ui.detail.food.DetailFoodActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.getViewModel
import org.koin.core.context.loadKoinModules
@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteFoodViewModel = getViewModel(),
    navigateToDetail: (String) -> Unit
) {
    viewModel.uiStateFavorite.collectAsState(
        initial = UiState.Loading
    ).let { favorite ->
        when(favorite.value) {
            is UiState.Loading -> {
                viewModel.getAllFavorite()
                Toast.makeText(LocalContext.current, "Sedang Loading", Toast.LENGTH_SHORT).show()
            }
            is UiState.Success -> {
                FavoriteContent(
                    favorites = (favorite.value as UiState.Success<List<Cooking>>).data,
                    navigateToDetail = navigateToDetail,
                    modifier = modifier,
                )
            }
            is UiState.Error -> {
                Toast.makeText(LocalContext.current, "Uppss terjadi kesalahan favorite", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun FavoriteContent(
    favorites: List<Cooking>,
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.3f)
    ) {
        items(favorites) { favorite ->
            ItemFoodsHorizontal (
                urlToImage =  favorite.thumb,
                title = favorite.title,
                publishedAt = favorite.times,
                difficulty = favorite.difficulty,
                tags = favorite.tags,
                onItemClick = { navigateToDetail(favorite.cookingID.toString()) }
            )
        }
    }
}