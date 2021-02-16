package com.example.foodapplication

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.core.data.CookingRepository
import com.example.core.di.foodDatabase
import com.example.core.di.networkModule
import com.example.core.di.repositoryModule
import com.example.core.domain.repository.IFoodRepository
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

@RunWith(AndroidJUnit4::class)
class RepositoryTest:KoinTest {

    private val repository: IFoodRepository by inject()

    @Before
    fun setup(){
        loadKoinModules(repositoryModule)
    }

    @After
    fun finish(){
        stopKoin()
    }

    @Test
    fun testCookingAll(){
        val dataCooking1 = repository.getAllCooking()
        Assert.assertNotNull(dataCooking1)
    }
}