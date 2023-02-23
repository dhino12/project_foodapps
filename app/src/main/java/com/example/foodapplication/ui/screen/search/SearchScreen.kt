package com.example.foodapplication.ui.screen.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.core.data.Resource
import com.example.core.domain.model.Search
import com.example.foodapplication.R
import com.example.foodapplication.ui.common.UiState
import com.example.foodapplication.ui.components.ItemFoodsHorizontal
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    toDetail: (String) -> Unit,
    viewModel: SearchViewModel = koinViewModel()
) {
    var query by remember { mutableStateOf("") }

    SearchContent(
        datasSearch = listOf(),
        toDetail= toDetail,
        query = { input -> query = input }
    )

    viewModel.uiStateSearch.collectAsState(initial = UiState.Loading).value.let {
        searchItem -> when (searchItem) {
            is UiState.Loading -> {
                viewModel.getSearch(query = query)
            }
            is UiState.Success -> {
                when (searchItem.data) {
                    is Resource.Loading -> {
                        viewModel.getSearch(query = query)
                    }
                    is Resource.Success -> {
                        SearchContent(
                            datasSearch = searchItem.data.data!!,
                            toDetail= toDetail,
                            query = { input -> viewModel.getSearch(input) }
                        )
                    }
                    is Resource.Error -> {}
                    else -> {}
                }
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun SearchContent(
    datasSearch: List<Search>,
    toDetail: (String) -> Unit,
    query: (String) -> Unit,
) {
    var search by remember { mutableStateOf("") }

    Image(
        painter = painterResource(R.drawable.background_home),
        contentDescription = "background_wave",
        contentScale = ContentScale.FillHeight,
    )
    ConstraintLayout (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(vertical = 30.dp, horizontal = 20.dp),
    ) {
        val (searchField, textView, itemList) = createRefs()

        OutlinedTextField(
            value = search,
            onValueChange =  { newInput ->
                search = newInput
                query(search)
            },
            placeholder = { Text(stringResource(R.string.search)) },
            label = { Text(text = "Mau masak apa ?....") },
            modifier = Modifier
                .constrainAs(searchField) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                },
        )

        Text(
            text = stringResource(R.string.search_food_word),
            modifier = Modifier
                .constrainAs(textView) {
                    start.linkTo(parent.start)
                    top.linkTo(searchField.bottom)
                    end.linkTo(parent.end)
                }
                .padding(top = 50.dp)
        )

        Card(
            shape = RoundedCornerShape(5.dp),
            elevation = 4.dp,
            modifier = Modifier
                .padding(top = 100.dp)
                .constrainAs(itemList) {
                    top.linkTo(searchField.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(160.dp),
                contentPadding = PaddingValues(25.dp),
                horizontalArrangement = Arrangement.spacedBy(26.dp),
                verticalArrangement = Arrangement.spacedBy(26.dp),
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                items(datasSearch) { cookingSearch ->
                    ItemFoodsHorizontal(
                        title = cookingSearch.title,
                        publishedAt = cookingSearch.times,
                        urlToImage = cookingSearch.thumb,
                        difficulty = cookingSearch.difficulty,
                        key = cookingSearch.cookingID,
                        onItemClick = toDetail
                    )
                }
            }
        }
    }
}

@Preview (showBackground = true)
@Composable
fun SearchContentPreview() {
    MaterialTheme {
        SearchContent(
            datasSearch = listOf(
                Search(
                    title = "",
                    cookingID = "",
                    thumb = "",
                    times = "",
                    portion = "",
                    difficulty = "",
                )
            ),
            toDetail = {},
            query = { }
        )
    }
}