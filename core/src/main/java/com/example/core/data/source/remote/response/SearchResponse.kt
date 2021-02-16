package com.example.core.data.source.remote.response

import com.google.gson.annotations.SerializedName


data class SearchResponse(

        @field:SerializedName("results")
        val results: List<ResultsItemSearch>? = null
)

data class ResultsItemSearch(

        @field:SerializedName("times")
        val times: String? = null,

        @field:SerializedName("thumb")
        val thumb: String? = null,

        @field:SerializedName("serving")
        val portion: String? = null,

        @field:SerializedName("title")
        val title: String? = null,

        @field:SerializedName("key")
        val key: String? = null,

        @field:SerializedName("difficulty")
        val difficulty: String? = null
)
