package com.example.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(

        val category: String? = null,
        val key: String,
):Parcelable