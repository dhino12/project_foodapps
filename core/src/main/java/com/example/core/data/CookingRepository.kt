package com.example.core.data

import android.util.Log
import com.example.core.data.source.local.LocalDataSource
import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.response.*
import com.example.core.domain.model.Article
import com.example.core.domain.model.Category
import com.example.core.domain.model.Cooking
import com.example.core.domain.model.Search
import com.example.core.domain.repository.IFoodRepository
import com.example.core.util.AppExecutors
import com.example.core.util.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class CookingRepository(
        private val remoteDataSource: RemoteDataSource,
        private val localDataSource: LocalDataSource,
        private val appExecutors: AppExecutors
) : IFoodRepository {

    override fun getAllCooking(): Flow<Resource<List<Cooking>>> =
            object : NetworkBoundResource<List<Cooking>, List<ResultsItemCooking>>() {
                override fun loadFromDB(): Flow<List<Cooking>> {
                    return localDataSource.getAllCooking().map { DataMapper.mapEntitiesCookingToDomain(it) }
                }

                override fun shouldFetch(data: List<Cooking>?): Boolean {
                    return data == null || data.isEmpty()
                }

                override suspend fun createCall(): Flow<ApiResponse<List<ResultsItemCooking>>> {
                    return remoteDataSource.getAllCooking()
                }

                override suspend fun saveCallResult(data: List<ResultsItemCooking>) {
                    val cookList = DataMapper.mapResponseToEntityCooking(data)
                    localDataSource.insertCooking(cookList)
                }
            }.asFlow()

    override fun getAllArticle(): Flow<Resource<List<Article>>> =
            object : NetworkBoundResource<List<Article>, List<ResultItemArticle>>() {
                override fun loadFromDB(): Flow<List<Article>> =
                        localDataSource.getAllArticle().map { DataMapper.mapEntitiesArticleToDomain(it) }

                override fun shouldFetch(data: List<Article>?): Boolean =
                        data == null || data.isEmpty()

                override suspend fun createCall(): Flow<ApiResponse<List<ResultItemArticle>>> =
                        remoteDataSource.getAllArticle()

                override suspend fun saveCallResult(data: List<ResultItemArticle>) {
                    val articleList = DataMapper.mapResponseToEntityArticle(data)
                    localDataSource.insertArticle(articleList)
                }

            }.asFlow()

    override fun getDetailCooking(key: List<String>): Flow<Resource<Cooking>> =
            object : NetworkBoundResource<Cooking, ResultsDetailCooking>() {
                override fun loadFromDB(): Flow<Cooking> {
                    Log.e("errorLoadFromDB", key[0])
                    Log.e("errorLoadFromDB", localDataSource.getDetailCooking(key[0]).toString())

                    return localDataSource.getDetailCooking(key[0]).map {
                        DataMapper.mapEntitiesCookingDetailToDomain(it)
                    }
                }

                override fun shouldFetch(data: Cooking?): Boolean {
                    Log.e("errorShouldRepo", (data == null).toString())
                    return data?.title == null
                }

                override suspend fun createCall(): Flow<ApiResponse<ResultsDetailCooking>> {
                    Log.e("errorCreateCall", "error")
                    return remoteDataSource.getDetailCooking(key[1])
                }

                override suspend fun saveCallResult(data: ResultsDetailCooking) {

                    val detailCooking = DataMapper.mapResponseToEntityCookingDetail(data)
                    localDataSource.insertDetailCooking(detailCooking)
                }
            }.asFlow()

    override fun getDetailArticle(key: List<String>): Flow<Resource<Article>> =
            object : NetworkBoundResource<Article, ResultDetailArticle>() {
                override fun loadFromDB(): Flow<Article> =
                        localDataSource.getDetailArticle(key[0]).map { DataMapper.mapEntityArticleDetailToDomain(it) }

                override fun shouldFetch(data: Article?): Boolean =
                        data?.title == null

                override suspend fun createCall(): Flow<ApiResponse<ResultDetailArticle>> =
                        remoteDataSource.getDetailArticle(key[1])

                override suspend fun saveCallResult(data: ResultDetailArticle) {
                    val detailArticle = DataMapper.mapResponseToEntityArticleDetail(data)
                    localDataSource.insertDetailArticle(detailArticle)

                }
            }.asFlow()

    override fun getListCategory(): Flow<Resource<List<Category>>> =
            object : NetworkBoundResource<List<Category>, List<ResultsItemCategory>>() {
                override fun loadFromDB(): Flow<List<Category>> =
                        localDataSource.getAllListCategory().map { DataMapper.mapEntityListCategoryToDomain(it) }

                override fun shouldFetch(data: List<Category>?): Boolean =
                        data.isNullOrEmpty()

                override suspend fun createCall(): Flow<ApiResponse<List<ResultsItemCategory>>> =
                        remoteDataSource.getListCategory()

                override suspend fun saveCallResult(data: List<ResultsItemCategory>) {
                    val listCategory = DataMapper.mapResponseToEntityListCategory(data)
                    localDataSource.insertListCategory(listCategory)

                }
            }.asFlow()

    override fun getAllContentCategory(tag: String): Flow<Resource<List<Cooking>>> =
            object : NetworkBoundResource<List<Cooking>, List<ResultsItemCooking>>() {
                override fun loadFromDB(): Flow<List<Cooking>> =
                        localDataSource.getAllContentCategory(tag).map { DataMapper.mapEntitiesContentCategoryToDomain(it) }

                override fun shouldFetch(data: List<Cooking>?): Boolean {
                    var on = true
                    data?.forEach {
                        on = it.tags != tag && data.isNullOrEmpty()
                    }
                    Log.e("error shouldFetch2", on.toString())
                    return on
                }

                override suspend fun createCall(): Flow<ApiResponse<List<ResultsItemCooking>>> {
                    return remoteDataSource.getContentCategory(tag)
                }

                override suspend fun saveCallResult(data: List<ResultsItemCooking>) {
                    val cookList = DataMapper.mapResponseContentToEntityCooking(data, tag)
                    localDataSource.insertAllContentCategory(cookList)
                }
            }.asFlow()

    override fun getFavoriteFood(): Flow<List<Cooking>> =
            localDataSource.getFavoriteFood().map { DataMapper.mapEntitiesCookingFavoriteDetailToDomain(it) }

    override fun setFavoriteFood(food: Cooking, state: Boolean) {
        val foodEntity = DataMapper.mapDomainToEntityCooking(food)
        appExecutors.diskIO().execute { localDataSource.setFavoriteFood(foodEntity, state) }
    }

    override fun getSearchFood(query: String?): Flow<Resource<List<Search>>> =
            flow {
                if (query != null) {
                    remoteDataSource.getSearch(query).collect { response ->
                        Log.e("errorResponse Collect", "error SearchRepo $query")
                        when (response) {
                            is ApiResponse.Success -> {
                                emit(Resource.Success(DataMapper.mapResponseSearchToEntity(response.data)))
                                Log.e("errorSearchResponseSuccess", response.data.toString())
                            }
                            is ApiResponse.Error -> {
                                Log.e("errorSearchResponseError", response.errorMessage)
                                emit(Resource.Error<List<Search>>(response.errorMessage))
                            }
                            else -> Log.e("errorSearchResponse", "$response")
                        }
                    }
                } else {
                    Log.e("errorResponse SearchRESPONSE", "error SearchRepo $query")
                }
            }

}