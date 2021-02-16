package com.example.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListCategoryResponse(
        @field:SerializedName("results")
        val results: List<ResultsItemCategory>? = null
)

data class ResultsItemCategory(
        @field:SerializedName("category")
        val category: String? = null,

        @field:SerializedName("key")
        val key: String? = null,

        )
