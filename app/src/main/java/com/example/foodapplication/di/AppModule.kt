package com.example.foodapplication.di

import com.example.core.domain.usecase.FoodInteractor
import com.example.core.domain.usecase.FoodUseCase
import com.example.foodapplication.ui.screen.category.ListCategoryViewModel
import com.example.foodapplication.ui.screen.category.ContentCategoryViewModel
import com.example.foodapplication.ui.detail.food.DetailViewModel
import com.example.foodapplication.ui.screen.favorite.FavoriteFoodViewModel
import com.example.foodapplication.ui.screen.home.HomeScreenViewModel
import com.example.foodapplication.ui.screen.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<FoodUseCase> { FoodInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeScreenViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { ListCategoryViewModel(get()) }
    viewModel { ContentCategoryViewModel(get()) }
    viewModel { FavoriteFoodViewModel(get()) }
}