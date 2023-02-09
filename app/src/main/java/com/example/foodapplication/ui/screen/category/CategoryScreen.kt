package com.example.foodapplication.ui.screen.category

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.core.data.Resource
import com.example.core.domain.model.Category
import com.example.foodapplication.ui.theme.Shapes
import com.example.foodapplication.R
import com.example.foodapplication.ui.common.UiState
import com.example.foodapplication.ui.components.ItemCardTitLe
import com.example.foodapplication.ui.components.loading.SkeletonCardTitle
import com.example.foodapplication.ui.theme.Orange200
import com.example.foodapplication.ui.theme.Red200
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.androidx.compose.getViewModel

@Composable
fun CategoryScreen (
    viewModel: ListCategoryViewModel = getViewModel(),
    navigateToListRecipeByCategory: (String) -> Unit
) {

    viewModel.uiStateCategory.collectAsState (initial = UiState.Loading)
        .value.let { categoryItem ->
            when (categoryItem) {
                is UiState.Loading -> {
                    viewModel.getAllCategory()
                }
                is UiState.Success -> {
                    when (categoryItem.data) {
                        is Resource.Loading -> {
                            SkeletonCardTitle(isLoading = true)
                        }
                        is Resource.Success -> {
                            CategoryContent(
                                categories = categoryItem.data.data!!,
                                navigateToListRecipeByCategory = navigateToListRecipeByCategory
                            )
                        }
                        is Resource.Error -> {}
                    }
                }
                is UiState.Error -> {}
            }
        }
}

@Composable
fun CategoryContent (
    modifier: Modifier = Modifier,
    categories: List<Category>,
    navigateToListRecipeByCategory: (String) -> Unit
) {
    TopAppBar(
        backgroundColor = Color(ContextCompat.getColor(LocalContext.current, R.color.young_red)),
        contentPadding = PaddingValues(12.dp),
        elevation = 5.dp,
    ) {
        Text(
            text = stringResource(id = R.string.title_category),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
        )
    }
    Image(
        painter = painterResource(id = R.drawable.chef_cooking),
        contentDescription = "animation cheff",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 80.dp)
            .clip(Shapes.small)
    )
    ConstraintLayout (
        modifier = modifier
            .padding(top = 320.dp)
            .fillMaxWidth()
    ) {
        val (card, topBar) = createRefs()

        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = 12.dp,
            backgroundColor = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(card) {
                    top.linkTo(topBar.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = modifier.padding(horizontal = 18.dp)
            ) {
                Card(
                    border = BorderStroke(2.dp, Color(
                        ContextCompat.getColor(LocalContext.current, R.color.young_red)
                    )),
                    modifier = modifier
                        .fillMaxWidth()
                        .height(11.dp)
                        .padding(end = 90.dp, bottom = 15.dp)
                ) {
                    Text(
                        text = "Daftar Category",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.SansSerif,
                        modifier = modifier
                            .padding(top = 18.dp, bottom = 2.dp)
                    )
                }

                LazyVerticalGrid(
                    columns = GridCells.Adaptive(160.dp),
                    contentPadding = PaddingValues(15.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = modifier.fillMaxWidth()
                ) {
                    items(categories) {category ->
                        if (category.category != null) {
                            ItemCardTitLe(
                                title = category.category!!,
                                key = category.key,
                                navigateToListDetail = navigateToListRecipeByCategory
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CategoryContentPreview() {
    CategoryContent(
        categories = listOf(
            Category(
            category = "makanan",
            key = "101"),

            Category(
                category = "minuman",
                key = "101"),
        ),
        navigateToListRecipeByCategory = {}
    )
}