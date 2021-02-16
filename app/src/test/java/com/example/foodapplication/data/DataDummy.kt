package com.example.foodapplication.data

import com.example.core.domain.model.Cooking

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

}