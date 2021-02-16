package com.example.core.domain.usecase

import com.example.core.data.Resource
import com.example.core.domain.model.Article
import com.example.core.domain.model.Category
import com.example.core.domain.model.Cooking
import com.example.core.domain.model.Search
import kotlinx.coroutines.flow.Flow

interface FoodUseCase {
    fun getAllCooking(): Flow<Resource<List<Cooking>>>

    fun getDetailCooking(key: List<String>): Flow<Resource<Cooking>>

    fun getAllArticle(): Flow<Resource<List<Article>>>

    fun getArticleDetail(key: List<String>): Flow<Resource<Article>>

    fun getListCategory(): Flow<Resource<List<Category>>>

    fun getAllContentCategory(tag: String): Flow<Resource<List<Cooking>>>

    fun getFavoriteFood(): Flow<List<Cooking>>

    fun setFavoriteFood(food: Cooking, state: Boolean)

    fun getSearchFood(query: String?): Flow<Resource<List<Search>>>
}