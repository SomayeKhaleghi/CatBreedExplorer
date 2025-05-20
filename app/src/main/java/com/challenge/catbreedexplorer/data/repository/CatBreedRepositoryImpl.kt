package com.challenge.catbreedexplorer.data.repository

import com.challenge.catbreedexplorer.data.local.catbreed.CatBreedDao
import com.challenge.catbreedexplorer.data.local.catbreed.CatBreedEntity
import com.challenge.catbreedexplorer.data.local.catbreed.toDomain
import com.challenge.catbreedexplorer.data.local.catbreed.toEntity
import com.challenge.catbreedexplorer.data.remote.ApiService
import com.challenge.catbreedexplorer.data.remote.catbreed.toDomain
import com.challenge.catbreedexplorer.domain.model.CatBreed
import com.challenge.catbreedexplorer.domain.repository.CatBreedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class CatBreedRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val catBreedDao: CatBreedDao
) : CatBreedRepository{
    override suspend fun refreshCatBreeds() {
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

    override fun getCatBreeds(): Flow<List<CatBreed>> {
        return catBreedDao.getAllCatBreeds()
            .catch { emit(emptyList()) } // Handle any database errors
            .map { entities ->
                entities.map { it.toDomain()}
            }
    }
}