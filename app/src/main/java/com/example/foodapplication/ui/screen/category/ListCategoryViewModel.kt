package com.example.foodapplication.ui.screen.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.core.data.Resource
import com.example.core.domain.model.Category
import com.example.core.domain.model.Cooking
import com.example.core.domain.usecase.FoodUseCase
import com.example.foodapplication.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ListCategoryViewModel(private val foodUseCase: FoodUseCase) : ViewModel() {
    private val _uiStateCategory: MutableStateFlow<UiState<Resource<List<Category>>>> =
        MutableStateFlow(UiState.Loading)
    val uiStateCategory: StateFlow<UiState<Resource<List<Category>>>> get() = _uiStateCategory

    fun getAllCategory() {
        viewModelScope.launch {
            foodUseCase.getListCategory()
                .catch {
                    _uiStateCategory.value = UiState.Error(it.message.toString())
                }
                .collect {category ->
                    _uiStateCategory.value = UiState.Success(category)
                }
        }
    }
}