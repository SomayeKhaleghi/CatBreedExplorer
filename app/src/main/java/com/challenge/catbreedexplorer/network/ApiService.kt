package com.challenge.catbreedexplorer.network

import com.challenge.catbreedexplorer.network.model.CatBreedDto
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("v1/breeds")
    suspend fun getCatBreeds(): Response<List<CatBreedDto>>
}
