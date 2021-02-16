package com.example.foodapplication.ui.search

import androidx.lifecycle.*
import com.example.core.data.Resource
import com.example.core.domain.model.Search
import com.example.core.domain.usecase.FoodUseCase

class SearchViewModel(private val foodUseCase: FoodUseCase) : ViewModel() {
    private val querySearch = MutableLiveData<String>()
    fun setQuerySearch(query: String) {
        this.querySearch.value = query
    }

    val search: LiveData<Resource<List<Search>>> = Transformations.switchMap(querySearch) { query ->
        foodUseCase.getSearchFood(query).asLiveData()
    }
}