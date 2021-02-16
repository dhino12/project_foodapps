package com.example.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articleDetail")
data class ArticleDetailEntity(

        val thumb: String? = null,

        val datePublished: String? = null,

        val author: String? = null,

        val description: String? = null,

        @PrimaryKey
        val title: String

)