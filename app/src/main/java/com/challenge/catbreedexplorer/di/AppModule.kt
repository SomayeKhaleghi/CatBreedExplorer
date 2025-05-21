package com.challenge.catbreedexplorer.di

import android.content.Context
import androidx.room.Room
import com.challenge.catbreedexplorer.BuildConfig
import com.challenge.catbreedexplorer.data.local.catbreed.CatBreedDao
import com.challenge.catbreedexplorer.data.local.CatDatabase
import com.challenge.catbreedexplorer.data.local.catimage.CatImageDao
import com.challenge.catbreedexplorer.data.remote.ApiService
import com.challenge.catbreedexplorer.data.repository.CatBreedRepositoryImpl
import com.challenge.catbreedexplorer.data.repository.CatDetailRepositoryImpl
import com.challenge.catbreedexplorer.data.repository.CatImageRepositoryImpl
import com.challenge.catbreedexplorer.domain.repository.CatBreedRepository
import com.challenge.catbreedexplorer.domain.repository.CatDetailRepository
import com.challenge.catbreedexplorer.domain.repository.CatImageRepository
import com.challenge.catbreedexplorer.utils.NetworkChecker
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

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

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

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CatDatabase {
        return Room.databaseBuilder(
            context,
            CatDatabase::class.java,
            "cat.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideCatBreedDao(database: CatDatabase): CatBreedDao {
        return database.catBreedDao()
    }

    @Provides
    @Singleton
    fun provideCatImageDao(database: CatDatabase): CatImageDao {
        return database.catImageDao()
    }

    @Provides
    @Singleton
    fun provideCatImageRepository(
        apiService: ApiService,
        catImageDao: CatImageDao
    ): CatImageRepository {
        return CatImageRepositoryImpl(apiService, catImageDao)
    }

    @Provides
    @Singleton
    fun provideCatBreedRepository(
        apiService: ApiService,
        catBreedDao: CatBreedDao
    ): CatBreedRepository {
        return CatBreedRepositoryImpl(apiService, catBreedDao)
    }

    @Provides
    @Singleton
    fun provideCatDetailRepository(
        dao: CatBreedDao,
        apiService: ApiService
    ): CatDetailRepository {
        return CatDetailRepositoryImpl(dao, apiService)
    }

    @Provides
    @Singleton
    fun provideNetworkChecker(@ApplicationContext context: Context): NetworkChecker {
        return NetworkChecker(context)
    }

}
