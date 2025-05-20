package com.challenge.catbreedexplorer.data

import com.challenge.catbreedexplorer.data.local.CatBreedDao
import com.challenge.catbreedexplorer.data.local.CatBreedEntity
import com.challenge.catbreedexplorer.data.local.toDomain
import com.challenge.catbreedexplorer.data.local.toEntity
import com.challenge.catbreedexplorer.data.remote.ApiService
import com.challenge.catbreedexplorer.data.remote.toDomain
import com.challenge.catbreedexplorer.model.CatBreed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatRepository @Inject constructor(
    private val apiService: ApiService,
    private val catBreedDao: CatBreedDao
) {

    /**
     * Fetch cat breeds from API (List<CatBreedDto>) and save to local database (List<CatBreedEntity>)
     */
    suspend fun refreshCatBreeds() {
        val apiResponse = apiService.getCatBreeds()
        if (apiResponse.isSuccessful) {
            apiResponse.body()?.let { catBreedsDto ->
                val catBreeds = catBreedsDto.map { it.toDomain() }
                val entities: List<CatBreedEntity> = catBreeds.map { it.toEntity() }
                catBreedDao.clearAllCatBreeds() // Clear old data
                catBreedDao.insertCatBreeds(entities) // Save new data
            }
        } else {
            throw Exception("Failed to fetch data from API")
        }
    }

    /**
     * Get all cat breeds from local database (Room) using Flow
     */
    fun getCatBreeds(): Flow<List<CatBreed>> {
        return catBreedDao.getAllCatBreeds()
            .catch { emit(emptyList()) } // Handle any database errors
            .map { entities ->
                entities.map { it.toDomain()}
            }
    }
}
