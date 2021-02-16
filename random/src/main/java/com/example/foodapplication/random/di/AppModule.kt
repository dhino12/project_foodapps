package com.example.foodapplication.random.di

import com.example.foodapplication.random.ui.RandomViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { RandomViewModel(get()) }
}