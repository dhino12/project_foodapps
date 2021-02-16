package com.example.core.data.source.local.room

import androidx.room.*
import com.example.core.data.source.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CookingDao {

    // Cooking Entity ========================

    @Query("SELECT * FROM cooking")
    fun getAllCooking(): Flow<List<CookingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCooking(cook: List<CookingEntity>)

    @Query("SELECT * FROM cookingDetail WHERE title = :titleCook")
    fun getDetailCooking(titleCook: String): Flow<CookingDetailEntity>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCookingDetail(cookData: CookingDetailEntity)

    @Query("SELECT * FROM cookingDetail WHERE isFavorite = 1")
    fun getFavoriteCook(): Flow<List<CookingDetailEntity>>

    @Update
    fun updateFavoriteCook(cook: CookingDetailEntity)

    // Article Entity ========================

    @Query("SELECT * FROM article")
    fun getAllArticle(): Flow<List<ArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(cook: List<ArticleEntity>)

    @Query("SELECT * FROM articleDetail WHERE title = :titleArticle")
    fun getDetailArticle(titleArticle: String): Flow<ArticleDetailEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticleDetail(articleData: ArticleDetailEntity)

    // Category Entity ===========================

    @Query("SELECT * FROM listCategory")
    fun getAllListCategory(): Flow<List<ListCategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListCategory(listCategoryData: List<ListCategoryEntity>)

    @Query("SELECT * FROM cooking WHERE tags = :tag")
    fun getContentCategory(tag: String): Flow<List<CookingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContentCategory(listContentCategory: List<CookingEntity>)

}