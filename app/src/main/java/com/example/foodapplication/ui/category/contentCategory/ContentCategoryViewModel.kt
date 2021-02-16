package com.example.foodapplication.ui.category.contentCategory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.usecase.FoodUseCase

class ContentCategoryViewModel(foodUseCase: FoodUseCase) : ViewModel() {
    private val tagContent = MutableLiveData<String>()

    fun setSelectedCategory(tags: String?) {
        this.tagContent.value = tags
    }

    val contentCategory = Transformations.switchMap(tagContent) { tag ->
        foodUseCase.getAllContentCategory(tag).asLiveData()
    }
}