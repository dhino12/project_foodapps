package com.example.foodapplication.ui.navigation

sealed class Screen (val route: String) {
    object Home:Screen("home")
    object Recipes:Screen("home/recipes")
    object Articles:Screen("home/articles")
    object Category: Screen("category")
    object Search: Screen("search")
    object Favorite: Screen("com.example.foodapplication.favorite.ui.FavoritesFragment")
    object DetailFood: Screen("home/{foodId}/?title={title}") {
        fun createRoute(foodId: String = "", title: String = "") = "home/$foodId/?title=$title"
    }
    object DetailArticle: Screen("home/{articleId}/?tag={tag}&title={title}") {
        fun createRoute(articleId: String, tag: String, title: String) = "home/$articleId/?tag=$tag&title=$title"
    }
    object RecipesByCategory: Screen("category/{category}") {
        fun createRoute(category: String) = "category/$category"
    }
}