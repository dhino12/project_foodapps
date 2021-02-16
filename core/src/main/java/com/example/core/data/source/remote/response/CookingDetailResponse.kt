package com.example.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class CookingDetailResponse(

        @field:SerializedName("results")
        val results: ResultsDetailCooking? = null
)

data class ResultsDetailCooking(

        @field:SerializedName("servings")
        val servings: String? = null,

        @field:SerializedName("times")
        val times: String? = null,

        @field:SerializedName("ingredient")
        val ingredient: List<String?>? = null,

        @field:SerializedName("thumb")
        val thumb: String? = null,

        @field:SerializedName("author")
        val author: Author? = null,

        @field:SerializedName("step")
        val step: List<String?>? = null,

        @field:SerializedName("title")
        val title: String,

        @field:SerializedName("dificulty")
        val difficulty: String? = null,

        @field:SerializedName("desc")
        val desc: String? = null
)

data class Author(

        @field:SerializedName("datePublished")
        val datePublished: String? = null,

        @field:SerializedName("user")
        val user: String? = null
)
