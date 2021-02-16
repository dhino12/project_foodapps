package com.example.foodapplication.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.data.Resource
import com.example.core.domain.model.Article
import com.example.core.domain.model.Cooking
import com.example.core.domain.usecase.FoodUseCase

class HomeViewModel(foodUseCase: FoodUseCase) : ViewModel() {

    val cook: LiveData<Resource<List<Cooking>>> = foodUseCase.getAllCooking().asLiveData()
    val article:LiveData<Resource<List<Article>>>  = foodUseCase.getAllArticle().asLiveData()
}