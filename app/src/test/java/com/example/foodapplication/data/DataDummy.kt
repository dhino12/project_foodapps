package com.example.foodapplication.data

import com.example.core.data.Resource
import com.example.core.domain.model.Cooking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object DataDummy {
    fun dummyAllCooking(): List<Cooking> =
        listOf(
            Cooking(
                cookingID = "it.cookingID",
                title = "it.title",
                thumb = "it.thumb",
                times = "it.times",
                servings = "it.portion",
                difficulty = "it.difficulty",
                tags = "it.tags"
            )
        )

    fun dummyAllCookingFlow():Flow<Resource<List<Cooking>>> =
        flow {
            try {
                emit(Resource.Success(
                    listOf(
                        Cooking(
                            cookingID = "it.cookingID",
                            title = "it.title",
                            thumb = "it.thumb",
                            times = "it.times",
                            servings = "it.portion",
                            difficulty = "it.difficulty",
                            tags = "it.tags"
                        )
                    )
                ))
            }catch (e:Exception){

            }
        }
}