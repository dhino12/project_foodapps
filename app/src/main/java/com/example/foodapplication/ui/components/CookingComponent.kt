package com.example.foodapplication.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.data.Resource
import com.example.core.domain.model.Cooking
import com.example.foodapplication.ui.components.loading.SkeletonItemVertical

@Composable
fun CookingComposable(
    modifier: Modifier = Modifier,
    cooking: Resource<List<Cooking>>? = null,
    navigateToDetailCooking: (String) -> Unit
) {
    /** TODO create a modifier so that it can be used on components when loading,
     * as well as full data so it's not complicated
     */
    when(cooking) {
        is Resource.Loading -> {
            SkeletonItemVertical(isLoading = true, modifier = modifier)
        }
        is Resource.Success -> {
            LazyHorizontalGrid(
                rows = GridCells.Adaptive(160.dp),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = modifier
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
        is Resource.Error -> {}
        else -> {}
    }
}


@Preview(showBackground = true)
@Composable
fun HomeContentPreview() {
    MaterialTheme {
        CookingComposable(navigateToDetailCooking = {})
    }
}