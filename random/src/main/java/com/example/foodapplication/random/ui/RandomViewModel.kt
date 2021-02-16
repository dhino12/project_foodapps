package com.example.foodapplication.random.ui

import androidx.lifecycle.*
import com.example.core.data.Resource
import com.example.core.domain.model.Cooking
import com.example.core.domain.usecase.FoodUseCase

class RandomViewModel(private val foodUseCase: FoodUseCase) : ViewModel() {

    private val dataCooking = MutableLiveData<List<String>>()

    fun setRandomData(keyRandom: List<String>) {
        this.dataCooking.value = keyRandom
    }

    var cookingData: LiveData<Resource<Cooking>> = Transformations.switchMap(dataCooking) { key ->
        foodUseCase.getDetailCooking(key).asLiveData()
    }

    fun setFavoriteFood(food: Cooking, newState: Boolean) = foodUseCase.setFavoriteFood(food, newState)
}