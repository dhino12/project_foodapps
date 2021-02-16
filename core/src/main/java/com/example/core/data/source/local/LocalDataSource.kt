package com.example.core.data.source.local

import com.example.core.data.source.local.entity.*
import com.example.core.data.source.local.room.CookingDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource constructor(private val cookingDao: CookingDao) {

    // =========== Cooking ===========
    fun getAllCooking(): Flow<List<CookingEntity>> = cookingDao.getAllCooking()

    suspend fun insertCooking(cookingList: List<CookingEntity>) = cookingDao.insertCooking(cookingList)

    fun getDetailCooking(title: String): Flow<CookingDetailEntity> = cookingDao.getDetailCooking(title)

    suspend fun insertDetailCooking(cookData: CookingDetailEntity) = cookingDao.insertCookingDetail(cookData)

    fun getFavoriteFood(): Flow<List<CookingDetailEntity>> = cookingDao.getFavoriteCook()

    fun setFavoriteFood(cooking: CookingDetailEntity, newState: Boolean) {
        cooking.isFavorite = newState
        cookingDao.updateFavoriteCook(cooking)
    }

    // =========== Article ===========
    fun getAllArticle(): Flow<List<ArticleEntity>> = cookingDao.getAllArticle()

    suspend fun insertArticle(articleList: List<ArticleEntity>) = cookingDao.insertArticle(articleList)

    suspend fun insertDetailArticle(articleData: ArticleDetailEntity) = cookingDao.insertArticleDetail(articleData)

    fun getDetailArticle(title: String): Flow<ArticleDetailEntity> = cookingDao.getDetailArticle(title)

    // =========== Category ===========
    fun getAllListCategory(): Flow<List<ListCategoryEntity>> = cookingDao.getAllListCategory()

    suspend fun insertListCategory(listCategory: List<ListCategoryEntity>) = cookingDao.insertListCategory(listCategory)

    fun getAllContentCategory(tag: String): Flow<List<CookingEntity>> = cookingDao.getContentCategory(tag)

    suspend fun insertAllContentCategory(content: List<CookingEntity>) = cookingDao.insertContentCategory(content)
}