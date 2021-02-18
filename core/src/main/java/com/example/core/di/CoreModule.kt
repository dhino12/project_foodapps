package com.example.core.di

import androidx.room.Room
import com.example.core.data.CookingRepository
import com.example.core.data.source.local.LocalDataSource
import com.example.core.data.source.local.room.CookingDatabase
import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.data.source.remote.network.ApiService
import com.example.core.domain.repository.IFoodRepository
import com.example.core.util.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
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
        val passpharse:ByteArray = SQLiteDatabase.getBytes("Tari_legong".toCharArray())
        val factory = SupportFactory(passpharse)
        Room.databaseBuilder(
                androidContext(),
                CookingDatabase::class.java, "cooking.db"
        ).fallbackToDestructiveMigration()
                .openHelperFactory(factory)
                .build()
    }
}

val networkModule = module {
    single {
        val hostname = "masak-apa.tomorisakura.vercel.app"
        val certificatePinner = CertificatePinner.Builder()
                .add(hostname, "sha256/95f2b7e3d2e57d7a0a1d0d4c9d80fc3c8d18b75cc47f87ae40ab182d8c161aae")
                .add(hostname, "sha256/RGAPlLRQcn1xdnOcTeachA2DqeJsIVoaph5br+UlEpA=")
                .add(hostname, "sha256/jQJTbIh0grw0/1TkHSumWb+Fs0Ggogr621gT3PvPKG0=")
                .build()
        OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .certificatePinner(certificatePinner)
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