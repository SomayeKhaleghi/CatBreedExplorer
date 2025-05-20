package com.challenge.catbreedexplorer.data.remote

import com.challenge.catbreedexplorer.data.remote.catbreed.CatBreedDto
import com.challenge.catbreedexplorer.data.remote.catimage.CatImageDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v1/breeds")
    suspend fun getCatBreeds(): Response<List<CatBreedDto>>

    @GET("v1/images/search")
    suspend fun getCatImages(
        @Query("breed_ids") breedId: String,
        @Query("limit") limit: Int = 100
    ): Response<List<CatImageDto>>
}
