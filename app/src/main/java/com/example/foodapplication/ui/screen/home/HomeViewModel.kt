package com.example.foodapplication.ui.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.Resource
import com.example.core.domain.model.Article
import com.example.core.domain.model.Cooking
import com.example.core.domain.usecase.FoodUseCase
import com.example.foodapplication.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val foodUseCase: FoodUseCase) : ViewModel() {

    private val _uiStateCooking: MutableStateFlow<UiState<Resource<List<Cooking>>>> = MutableStateFlow(UiState.Loading)
    val uiStateCooking: StateFlow<UiState<Resource<List<Cooking>>>> get() = _uiStateCooking

    private val _uiStateArticle: MutableStateFlow<UiState<Resource<List<Article>>>> = MutableStateFlow(UiState.Loading)
    val uiStateArticle: StateFlow<UiState<Resource<List<Article>>>> get() = _uiStateArticle

    fun getAllItemRecipe() {
        viewModelScope.launch {
            foodUseCase.getAllCooking()
                .catch {
                    _uiStateCooking.value = UiState.Error(it.message.toString())
                }
                .collect { cooking ->
                    _uiStateCooking.value = UiState.Success(cooking)
                }
        }

        viewModelScope.launch {
            foodUseCase.getAllArticle()
                .catch {
                    _uiStateArticle.value = UiState.Error(it.message.toString())
                }
                .collect { article ->
                    _uiStateArticle.value = UiState.Success(article)
                }
        }
    }

    fun getAllItemArticle() {
        viewModelScope.launch {
            foodUseCase.getAllArticle()
                .catch {
                    _uiStateArticle.value = UiState.Error(it.message.toString())
                }
                .collect { article ->
                    _uiStateArticle.value = UiState.Success(article)
                }
        }
    }
}