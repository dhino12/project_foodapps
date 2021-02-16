package com.example.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.data.source.local.entity.*

@Database(
        entities = [
            CookingEntity::class,
            ArticleEntity::class,
            CookingDetailEntity::class,
            ArticleDetailEntity::class,
            ListCategoryEntity::class
        ],
        version = 1, exportSchema = false
)
abstract class CookingDatabase : RoomDatabase() {

    abstract fun cookingDao(): CookingDao
}