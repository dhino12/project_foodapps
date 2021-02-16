package com.example.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ArticleDetailResponse(

        @field:SerializedName("results")
        val results: ResultDetailArticle? = null
)

data class ResultDetailArticle(

        @field:SerializedName("thumb")
        val thumb: String? = null,

        @field:SerializedName("date_published")
        val datePublished: String? = null,

        @field:SerializedName("author")
        val author: String? = null,

        @field:SerializedName("description")
        val description: String? = null,

        @field:SerializedName("title")
        val title: String
)
