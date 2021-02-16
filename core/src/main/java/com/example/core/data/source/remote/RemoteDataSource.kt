package com.example.core.data.source.remote

import android.util.Log
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.network.ApiService
import com.example.core.data.source.remote.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource constructor(private val apiService: ApiService) {

    suspend fun getAllCooking(): Flow<ApiResponse<List<ResultsItemCooking>>> {
        return flow {
            try {
                val client = apiService.getCooking(1)
                val dataArray = client.results
                if (dataArray.isNullOrEmpty()) {
                    emit(ApiResponse.Empty)
                } else {
                    emit(ApiResponse.Success(dataArray))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                Log.e("error_RemoteDataSource", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailCooking(titleKey: String): Flow<ApiResponse<ResultsDetailCooking>> {

        return flow {
            try {
                val client = apiService.getDetailCooking(titleKey)
                val dataObject = client.results
                if (dataObject != null) {
                    emit(ApiResponse.Success(client.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                Log.e("error_RemoteDataSource DetailCooking", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)

    }

    suspend fun getAllArticle(): Flow<ApiResponse<List<ResultItemArticle>>> {
        val listCategory = arrayListOf("inspirasi-dapur", "makanan-gaya-hidup", "tips-masak")

        return flow {
            try {
                val client = apiService.getArticle(listCategory.random())
                val dataArray = client.results
                if (dataArray.isNullOrEmpty()) {
                    emit(ApiResponse.Empty)
                } else {
                    emit(ApiResponse.Success(dataArray))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                Log.e("error_RemoteDataSource ArticleList", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailArticle(titleKey: String): Flow<ApiResponse<ResultDetailArticle>> {

        return flow {
            try {
                val client = apiService.getDetailArticle("makanan-gaya-hidup", titleKey)
                val dataObject = client.results
                if (dataObject != null) {
                    emit(ApiResponse.Success(dataObject))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                Log.e("error_RemoteDataSource DetailArticle", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getListCategory(): Flow<ApiResponse<List<ResultsItemCategory>>> {

        return flow {
            try {
                val client = apiService.getListCategory()
                val dataArray = client.results
                if (dataArray.isNullOrEmpty()) {
                    emit(ApiResponse.Empty)
                } else {
                    emit(ApiResponse.Success(dataArray))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                Log.e("error_RemoteDataSource CategoryList", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getContentCategory(tag: String): Flow<ApiResponse<List<ResultsItemCooking>>> {

        return flow {
            try {
                val client = apiService.getCategoryContent(tag)
                val dataArray = client.results
                if (dataArray.isNullOrEmpty()) {
                    emit(ApiResponse.Empty)
                } else {
                    emit(ApiResponse.Success(dataArray))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                Log.e("error_RemoteDataSource ContentCategoryList", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getSearch(querySearch: String): Flow<ApiResponse<List<ResultsItemSearch>>> {
        return flow {
            Log.e("error searchRemote", querySearch)
            try {
                val client = apiService.getSearch(querySearch)
                val dataArray = client.results
                if (dataArray.isNullOrEmpty()) {
                    Log.e("error_RemoteDataSource SearchFeature", dataArray.isNullOrEmpty().toString())
                    emit(ApiResponse.Empty)
                } else {
                    //Log.e("error_RemoteDataSource SearchRemoteSuccess", client.results.toString())
                    emit(ApiResponse.Success(dataArray))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                Log.e("error_RemoteDataSource SearchFeature", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}