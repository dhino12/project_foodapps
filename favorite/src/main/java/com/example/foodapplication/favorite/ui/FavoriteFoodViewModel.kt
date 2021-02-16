package com.example.foodapplication.favorite.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.usecase.FoodUseCase

class FavoriteFoodViewModel(foodUseCase: FoodUseCase) : ViewModel() {
    val favoriteData = foodUseCase.getFavoriteFood().asLiveData()
}