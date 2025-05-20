package com.challenge.catbreedexplorer.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v1/breeds")
    suspend fun getCatBreeds(): Response<List<CatBreedDto>>

    @GET("v1/images/search")
    suspend fun getBreedImages(
        @Query("breed_ids") breedId: String,
        @Query("limit") limit: Int = 100
    ): List<CatImageDto>
}
