package com.example.foodapplication.ui

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.foodapplication.R
import com.example.foodapplication.ui.detail.food.DetailFoodActivity
import com.example.foodapplication.ui.navigateion.NavigationItem
import com.example.foodapplication.ui.navigateion.Screen
import com.example.foodapplication.ui.screen.category.CategoryScreen
import com.example.foodapplication.ui.screen.category.ListRecipeByCategory
import com.example.foodapplication.ui.screen.favorite.FavoriteScreen
import com.example.foodapplication.ui.screen.home.HomeScreen
import com.example.foodapplication.ui.screen.home.HomeScreenListView
import com.example.foodapplication.ui.screen.search.SearchScreen

@Composable
fun RecipeApp (
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val context = LocalContext.current
    
    Scaffold(
        bottomBar = {
            if(currentRoute != Screen.DetailFood.route) {
                BottomBar(navController)
            }},
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetailCooking = { keyUrl ->
                        val keyDataCooking = keyUrl.split("&")
                        navController.navigate(Screen.DetailFood.createRoute(keyDataCooking[0], keyDataCooking[1]))
                    },
                    navigateToDetailArticle = { key ->
                        val keyData = key.split("&")
                        navController.navigate(Screen.DetailArticle.createRoute(keyData[1], keyData[0], keyData[2]))
                    },
                    navigateToCookingList = { navController.navigate(Screen.Recipes.route) },
                    navigateToArticleList = { navController.navigate(Screen.Articles.route) },
                    navigateToSearch = { navController.navigate(Screen.Search.route) }
                )
            }

            composable(Screen.Recipes.route) {
                HomeScreenListView(
                    indicator = "cooking",
                    navigateToDetail = { keyUrl ->
                            val keyDataCooking = keyUrl.split("&")
                            navController.navigate(Screen.DetailFood.createRoute(keyDataCooking[0], keyDataCooking[1]))
                    }
                )
            }

            composable(Screen.Category.route) {
                CategoryScreen(
                    navigateToListRecipeByCategory = { key ->
                        navController.navigate(Screen.RecipesByCategory.createRoute(key))
                    }
                )
            }

            composable(Screen.Favorite.route) {
                FavoriteScreen(
                    toDetail = { keyUrl ->
                        val keyDataCooking = keyUrl.split("&")
                        navController.navigate(Screen.DetailFood.createRoute(keyDataCooking[0], keyDataCooking[1]))
                    }
                )
            }

            composable(
                route = Screen.RecipesByCategory.route,
                arguments = listOf(navArgument("category") { type = NavType.StringType })
            ) {
                val category = it.arguments?.getString("category")
                ListRecipeByCategory(
                    category = category.toString(),
                    onItemToDetail = { keyUrl ->
                        Log.e("keyUrl", keyUrl)
                        val keyDataCooking = keyUrl.split("&")
                        navController.navigate(Screen.DetailFood.createRoute(keyDataCooking[0], keyDataCooking[1]))
                    }
                )
            }

            composable(Screen.Articles.route) {
                HomeScreenListView(
                    indicator = "article",
                    navigateToDetail = { key ->
                        val keyData = key.split("&")
                        navController.navigate(Screen.DetailArticle.createRoute(keyData[1], keyData[0], keyData[2]))
                    },
                )
            }

            composable(Screen.Search.route) {
                SearchScreen(
                    toDetail = { keyUrl ->
                        Log.e("keyUrl", keyUrl)
                        val keyDataCooking = keyUrl.split("&")
                        navController.navigate(Screen.DetailFood.createRoute(keyDataCooking[0], keyDataCooking[1]))
                    }
                )
            }

            composable(
                route = Screen.DetailFood.route,
                arguments = listOf(
                    navArgument("foodId") { type = NavType.StringType },
                    navArgument("title") { type = NavType.StringType }
                ),
            ) {
                val idCooking = it.arguments?.getString("foodId") ?: null
                val titleCooking = it.arguments?.getString("title") ?: null
                Log.e("dataCooking", "$idCooking ||| $titleCooking")

                LaunchedEffect(key1 = true ) {
                    val intent = Intent(context, DetailFoodActivity::class.java)
                    intent.putExtra(DetailFoodActivity.EXTRA_ID_COOKING, idCooking)
                    intent.putExtra(DetailFoodActivity.EXTRA_TITLE_COOKING, titleCooking)

                    context.startActivity(intent)
                }
            }
            composable(
                route = Screen.DetailArticle.route,
                arguments = listOf(
                    navArgument("articleId") { type = NavType.StringType },
                    navArgument("tag") { type = NavType.StringType },
                    navArgument("title") { type = NavType.StringType },
                )
            ) {
                val idArticle = it.arguments?.getString("articleId") ?: null
                val tagArticle = it.arguments?.getString("tag") ?: null
                val titleArticle = it.arguments?.getString("title") ?: null
                val context = LocalContext.current as Activity
                if (idArticle != null) {
                    LaunchedEffect(key1 = true) {
                        val intent = Intent(context, DetailFoodActivity::class.java)
                        intent.putExtra(DetailFoodActivity.EXTRA_ARTICLE_ID, idArticle)
                        intent.putExtra(DetailFoodActivity.EXTRA_ARTICLE_TAG, tagArticle)
                        intent.putExtra(DetailFoodActivity.EXTRA_ARTICLE_TITLE, titleArticle)
                        context.startActivity(intent)
                    }
                }
            }
        }
    }
}

@Composable
private fun BottomBar(
    navController:NavHostController,
    modifier: Modifier = Modifier
) {
    BottomNavigation (
        backgroundColor = Color(R.color.white),
        contentColor = Color(R.color.orange),
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.title_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(id = R.string.title_category),
                icon =  Icons.Default.List,
                screen = Screen.Category
            ),
            NavigationItem(
                title = stringResource(R.string.title_favorite),
                icon = Icons.Default.Favorite,
                screen = Screen.Favorite
            )
        )

        BottomNavigation {
            navigationItems.map { item ->
                BottomNavigationItem(
                    icon = { Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                        )},
                    label = { Text(text = item.title) },
                    selected = currentRoute == item.screen.route,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeAppPrev(){
    MaterialTheme {
        RecipeApp()
    }
}