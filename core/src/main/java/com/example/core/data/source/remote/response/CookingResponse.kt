package com.example.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class CookingResponse(

		@field:SerializedName("results")
		val results: List<ResultsItemCooking>? = null
)

data class ResultsItemCooking(

		@field:SerializedName("times")
		val times: String? = null,

		@field:SerializedName("thumb")
		val thumb: String? = null,

		@field:SerializedName("portion")
		val portion: String? = null,

		@field:SerializedName("title")
		val title: String? = null,

		@field:SerializedName("key")
		val key: String? = null,

		@field:SerializedName("dificulty")
		val difficulty: String? = null
)
