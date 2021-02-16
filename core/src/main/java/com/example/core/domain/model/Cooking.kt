package com.example.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cooking(
        val cookingID: String? = null,

        val servings: String? = null,

        val times: String? = null,

        val ingredient: String? = null,

        val thumb: String? = null,

        val author: String? = null,

        val step: String? = null,

        val title: String? = null,

        val difficulty: String? = null,

        val desc: String? = null,

        val tags: String? = null,

        val isFavorite: Boolean? = false
):Parcelable