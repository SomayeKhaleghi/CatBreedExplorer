package com.challenge.catbreedexplorer.di

import android.content.Context
import androidx.room.Room
import com.challenge.catbreedexplorer.BuildConfig
import com.challenge.catbreedexplorer.data.local.CatBreedDao
import com.challenge.catbreedexplorer.data.local.CatDatabase
import com.challenge.catbreedexplorer.data.remote.ApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://api.thecatapi.com/"

    // Gson Provider (For Retrofit)
    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    //  OkHttpClient with Logging and API Key Interceptor
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("x-api-key", BuildConfig.CAT_API_KEY)
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    //  Retrofit with OkHttpClient and Gson
    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    //  ApiService Provider (Retrofit Service)
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    //  Room Database Setup (Using KSP, No Kapt)
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CatDatabase {
        return Room.databaseBuilder(
            context,
            CatDatabase::class.java,
            "cat.db"
        ).fallbackToDestructiveMigration().build()
    }

    //  DAO Provider (CatBreedDao)
    @Provides
    fun provideCatBreedDao(database: CatDatabase): CatBreedDao {
        return database.catBreedDao()
    }
}
