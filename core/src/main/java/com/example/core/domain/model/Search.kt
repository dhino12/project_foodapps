package com.example.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Search(

        val cookingID: String,

        val title: String?,

        val thumb: String?,

        val times: String?,

        val portion: String?,

        val difficulty: String?
):Parcelable