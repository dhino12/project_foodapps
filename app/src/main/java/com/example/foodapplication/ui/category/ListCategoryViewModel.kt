package com.example.foodapplication.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.usecase.FoodUseCase

class ListCategoryViewModel(foodUseCase: FoodUseCase) : ViewModel() {
    val listCategory = foodUseCase.getListCategory().asLiveData()
}