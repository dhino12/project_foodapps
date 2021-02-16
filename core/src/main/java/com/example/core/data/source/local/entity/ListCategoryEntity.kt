package com.example.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "listCategory")
data class ListCategoryEntity(

        val category: String? = null,

        @PrimaryKey
        val key: String,
)