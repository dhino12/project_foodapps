package com.example.foodapplication.di

import com.example.core.domain.usecase.FoodInteractor
import com.example.core.domain.usecase.FoodUseCase
import com.example.foodapplication.ui.category.ListCategoryViewModel
import com.example.foodapplication.ui.category.contentCategory.ContentCategoryViewModel
import com.example.foodapplication.ui.detail.food.DetailViewModel
import com.example.foodapplication.ui.home.HomeViewModel
import com.example.foodapplication.ui.search.SearchViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<FoodUseCase> { FoodInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { ListCategoryViewModel(get()) }
    viewModel { ContentCategoryViewModel(get()) }
}