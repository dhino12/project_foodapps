package com.example.foodapplication.ui.screen.category

import androidx.lifecycle.*
import com.example.core.data.Resource
import com.example.core.domain.model.Cooking
import com.example.core.domain.usecase.FoodUseCase
import com.example.foodapplication.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ContentCategoryViewModel(private val foodUseCase: FoodUseCase) : ViewModel() {
    private val tagContent = MutableLiveData<String>()

    private val _uiStateCooking: MutableStateFlow<UiState<Resource<List<Cooking>>>> =
        MutableStateFlow(UiState.Loading)
    val uiStateCooking: StateFlow<UiState<Resource<List<Cooking>>>> get() = _uiStateCooking

    fun getAllCookingByCategory(category: String) {
        viewModelScope.launch {
            foodUseCase.getAllContentCategory(category)
                .catch {
                    _uiStateCooking.value = UiState.Error(it.toString())
                }
                .collect { cooking ->
                    _uiStateCooking.value = UiState.Success(cooking)
                }
        }
    }

    fun setSelectedCategory(tags: String?) {
        this.tagContent.value = tags
    }

    val contentCategory = Transformations.switchMap(tagContent) { tag ->
        foodUseCase.getAllContentCategory(tag).asLiveData()
    }
}