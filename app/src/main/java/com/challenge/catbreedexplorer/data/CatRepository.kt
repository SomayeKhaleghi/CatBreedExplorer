// data/CatRepository.kt
package com.challenge.catbreedexplorer.data

import com.challenge.catbreedexplorer.data.remote.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getCatBreeds() = apiService.getCatBreeds()
}
