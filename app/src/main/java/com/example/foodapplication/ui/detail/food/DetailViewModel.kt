package com.example.foodapplication.ui.detail.food

import android.util.Log
import androidx.lifecycle.*
import com.example.core.data.Resource
import com.example.core.domain.model.Article
import com.example.core.domain.model.Cooking
import com.example.core.domain.usecase.FoodUseCase

class DetailViewModel(private val foodUseCase: FoodUseCase) : ViewModel() {
    private var titleCooking = MutableLiveData<List<String>>()
    private var titleArticle = MutableLiveData<List<String>>()

    fun setSelectedCook(titleCookingDb: List<String>? = null, titleArticleDb: List<String>? = null) {

        Log.e("error contentTitleDb", titleCookingDb?.toList().toString())
        Log.e("error TitleContent", titleCookingDb?.size.toString())

        if (titleCookingDb.isNullOrEmpty()) {
            this.titleArticle.value = titleArticleDb
        } else if (titleArticleDb.isNullOrEmpty()) {
            this.titleCooking.value = titleCookingDb
        }
    }

    var cookingDetail: LiveData<Resource<Cooking>> = Transformations.switchMap(titleCooking) { title ->
        foodUseCase.getDetailCooking(title).asLiveData()
    }

    var articleDetail: LiveData<Resource<Article>> = Transformations.switchMap(titleArticle) { title ->
        foodUseCase.getArticleDetail(titleArticle.value!!).asLiveData()
    }

    fun setFavoriteFood(food: Cooking, newState: Boolean) = foodUseCase.setFavoriteFood(food, newState)
}