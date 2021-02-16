package com.example.foodapplication.favorite.ui

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { FavoriteFoodViewModel(get()) }
}