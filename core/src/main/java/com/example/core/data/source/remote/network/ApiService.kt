package com.example.core.data.source.remote.network

import com.example.core.data.source.remote.response.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/api/recipes/{page}")
    suspend fun getCooking(@Path("page") page: Int): CookingResponse

    @GET("/api/categorys/article/{tags}")
    suspend fun getArticle(@Path("tags") tags: String): ArticleResponse

    @GET("/api/recipe/{key}")
    suspend fun getDetailCooking(@Path("key") key: String): CookingDetailResponse

    @GET("/api/article/{tags}/{key}")
    suspend fun getDetailArticle(@Path("tags") tags: String, @Path("key") key: String): ArticleDetailResponse

    @GET("/api/categorys/recipes")
    suspend fun getListCategory(): ListCategoryResponse

    @GET("/api/categorys/recipes/{tag}")
    suspend fun getCategoryContent(@Path("tag") key: String): CookingResponse

    @GET("/api/search/")
    suspend fun getSearch(@Query("q") values: String): SearchResponse
}