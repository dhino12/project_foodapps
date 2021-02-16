package com.example.core.domain.usecase

import com.example.core.data.Resource
import com.example.core.domain.model.Article
import com.example.core.domain.model.Category
import com.example.core.domain.model.Cooking
import com.example.core.domain.model.Search
import com.example.core.domain.repository.IFoodRepository
import kotlinx.coroutines.flow.Flow

class FoodInteractor(private val foodRepository: IFoodRepository) : FoodUseCase {

    override fun getAllCooking(): Flow<Resource<List<Cooking>>> = foodRepository.getAllCooking()

    override fun getDetailCooking(key: List<String>): Flow<Resource<Cooking>> = foodRepository.getDetailCooking(key)

    override fun getAllArticle(): Flow<Resource<List<Article>>> = foodRepository.getAllArticle()

    override fun getArticleDetail(key: List<String>): Flow<Resource<Article>> = foodRepository.getDetailArticle(key)

    override fun getListCategory(): Flow<Resource<List<Category>>> = foodRepository.getListCategory()

    override fun getAllContentCategory(tag: String): Flow<Resource<List<Cooking>>> = foodRepository.getAllContentCategory(tag)

    override fun getFavoriteFood(): Flow<List<Cooking>> = foodRepository.getFavoriteFood()

    override fun setFavoriteFood(food: Cooking, state: Boolean) = foodRepository.setFavoriteFood(food, state)

    override fun getSearchFood(query: String?): Flow<Resource<List<Search>>> = foodRepository.getSearchFood(query)

}