package com.example.foodapplication.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.Cooking
import com.example.core.domain.usecase.FoodUseCase
import com.example.foodapplication.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteFoodViewModel(private val foodUseCase: FoodUseCase) : ViewModel() {
    private val _uiStateFavorite :MutableStateFlow<UiState<List<Cooking>>> =
        MutableStateFlow(UiState.Loading)
    val uiStateFavorite: StateFlow<UiState<List<Cooking>>> get() = _uiStateFavorite

    fun getAllFavorite() {
        viewModelScope.launch {
            foodUseCase.getFavoriteFood()
                .catch {
                    _uiStateFavorite.value = UiState.Error(it.message.toString())
                }
                .collect { favorite ->
                    _uiStateFavorite.value = UiState.Success(favorite)
                }
        }
    }
}