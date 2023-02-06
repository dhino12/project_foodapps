package com.example.foodapplication.ui.screen.detail

import android.util.Log
import androidx.lifecycle.*
import com.example.core.data.Resource
import com.example.core.domain.model.Article
import com.example.core.domain.model.Cooking
import com.example.core.domain.usecase.FoodUseCase
import com.example.foodapplication.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailViewModel(private val foodUseCase: FoodUseCase) : ViewModel() {
    private var titleCooking = MutableLiveData<List<String>>()
    private var titleArticle = MutableLiveData<List<String>>()

    private val _uiState: MutableStateFlow<UiState<Cooking>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Cooking>> get() = _uiState

    fun getFoodById(id: List<String>) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            foodUseCase.getDetailCooking(id)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { detailCooking ->
                    _uiState.value = UiState.Success(detailCooking.data!!)
                }

//            _uiState.value = UiState.Success(foodUseCase.getDetailCooking(id))
        }
    }

//    fun setSelectedCook(titleCookingDb: List<String>? = null, titleArticleDb: List<String>? = null) {
//
//        Log.e("error contentTitleDb", titleCookingDb?.toList().toString())
//        Log.e("error TitleContent", titleCookingDb?.size.toString())
//
//        if (titleCookingDb.isNullOrEmpty()) {
//            this.titleArticle.value = titleArticleDb
//        } else if (titleArticleDb.isNullOrEmpty()) {
//            this.titleCooking.value = titleCookingDb
//        }
//    }
//
//    var cookingDetail: LiveData<Resource<Cooking>> = Transformations.switchMap(titleCooking) { title ->
//        foodUseCase.getDetailCooking(title).asLiveData()
//    }
//
//    var articleDetail: LiveData<Resource<Article>> = Transformations.switchMap(titleArticle) { title ->
//        foodUseCase.getArticleDetail(titleArticle.value!!).asLiveData()
//    }

    fun setFavoriteFood(food: Cooking, newState: Boolean) = foodUseCase.setFavoriteFood(food, newState)
}