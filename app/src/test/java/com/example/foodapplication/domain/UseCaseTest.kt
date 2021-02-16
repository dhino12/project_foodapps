package com.example.foodapplication.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import com.example.core.data.Resource
import com.example.core.domain.model.Cooking
import com.example.core.domain.repository.IFoodRepository
import com.example.core.domain.usecase.FoodInteractor
import com.example.core.domain.usecase.FoodUseCase
import com.example.foodapplication.data.DataDummy
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UseCaseTest {

    private lateinit var foodUseCase:FoodUseCase

    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    @Mock
    private lateinit var foodRepository:IFoodRepository

    @Before
    fun setup(){
        foodUseCase = FoodInteractor(foodRepository)
        val dummyFood = Resource.Success(DataDummy.dummyAllCooking())
        val dataCooking = MutableLiveData<Resource<List<Cooking>>>()
        dataCooking.value = dummyFood

        `when`(foodRepository.getAllCooking()).thenReturn(dataCooking.asFlow())
    }

    @Test
    fun `get data from repository`(){
        foodUseCase.getAllCooking()
        verify(foodRepository).getAllCooking()
        Assert.assertNotNull(foodUseCase)
    }

}