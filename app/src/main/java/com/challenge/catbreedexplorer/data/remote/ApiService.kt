package com.challenge.catbreedexplorer.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("v1/breeds")
    suspend fun getCatBreeds(): Response<List<CatBreedDto>>
}
