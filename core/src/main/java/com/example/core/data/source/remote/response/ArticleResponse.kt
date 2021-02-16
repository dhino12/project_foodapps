package com.example.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ArticleResponse(
        @field:SerializedName("results")
        var results: List<ResultItemArticle>? = null
)

data class ResultItemArticle(
        @field:SerializedName("title")
        var title: String? = null,

        @field:SerializedName("thumb")
        var thumb: String? = null,

        @field:SerializedName("tags")
        var tags: String? = null,

        @field:SerializedName("key")
        var key: String? = null
)