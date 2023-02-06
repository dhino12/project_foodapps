package com.example.foodapplication.ui.screen.search

import androidx.lifecycle.*
import com.example.core.data.Resource
import com.example.core.domain.model.Search
import com.example.core.domain.usecase.FoodUseCase
import com.example.foodapplication.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class SearchViewModel(private val foodUseCase: FoodUseCase) : ViewModel() {

    private val _uiStateSearch: MutableStateFlow<UiState<Resource<List<Search>>>> = MutableStateFlow(
        UiState.Loading)
    val uiStateSearch: StateFlow<UiState<Resource<List<Search>>>> get() = _uiStateSearch

    fun getSearch(query: String) {
        viewModelScope.launch {
            foodUseCase.getSearchFood(query)
                .catch {
                    _uiStateSearch.value = UiState.Error(it.message.toString())
                }
                .collect { cooking ->
                    _uiStateSearch.value = UiState.Success(cooking)
                }
        }
    }

    private val querySearch = MutableLiveData<String>()
    fun setQuerySearch(query: String) {
        this.querySearch.value = query
    }

    val search: LiveData<Resource<List<Search>>> = Transformations.switchMap(querySearch) { query ->
        foodUseCase.getSearchFood(query).asLiveData()
    }
}