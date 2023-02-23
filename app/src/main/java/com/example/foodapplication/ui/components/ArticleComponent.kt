package com.example.foodapplication.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core.data.Resource
import com.example.core.domain.model.Article
import com.example.foodapplication.ui.components.loading.SkeletonItemVertical

@Composable
fun ArticleComposable(
    modifier: Modifier = Modifier,
    articles: Resource<List<Article>>? = null,
    navigateToDetailArticle: (String) -> Unit,
) {
    /** TODO create a modifier so that it can be used on components when loading,
     * as well as full data so it's not complicated
     */
    when(articles) {
        is Resource.Loading -> {
            SkeletonItemVertical(isLoading = true, modifier = modifier)
        }
        is Resource.Success -> {
            LazyHorizontalGrid(
                rows = GridCells.Adaptive(160.dp),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = modifier
            ) {
                if (articles?.data != null) {
                    items(articles.data!!) { article ->
                        Log.e("data", article.title.toString())
                        ItemFoodsVertical(
                            thumb = article.thumb,
                            title = article.title,
                            time = article.datePublished,
                            tags = article.tags,
                            OnItemClick = {
                                navigateToDetailArticle(
                                    "${article.key!!.replace("/", "&")}&${article.title}"
                                )
                            }
                        )
                    }
                }
            }
            Log.d("dataArticle", articles.data.toString())
        }
        is Resource.Error -> {}
        else -> {}
    }
}