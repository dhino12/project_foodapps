package com.example.core.di

import androidx.room.Room
import com.example.core.data.CookingRepository
import com.example.core.data.source.local.LocalDataSource
import com.example.core.data.source.local.room.CookingDatabase
import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.data.source.remote.network.ApiService
import com.example.core.domain.repository.IFoodRepository
import com.example.core.util.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val foodDatabase = module {
    factory {
        get<CookingDatabase>().cookingDao()
    }
    single {
        Room.databaseBuilder(
                androidContext(),
                CookingDatabase::class.java, "cooking.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
    }
    single {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://masak-apa.tomorisakura.vercel.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(get())
                .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single { AppExecutors() }
    single<IFoodRepository> { CookingRepository(get(), get(), get()) }
}