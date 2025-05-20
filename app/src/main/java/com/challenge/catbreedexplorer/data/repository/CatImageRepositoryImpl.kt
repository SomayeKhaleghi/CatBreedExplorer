package com.challenge.catbreedexplorer.data.repository

import com.challenge.catbreedexplorer.data.local.catimage.CatImageDao
import com.challenge.catbreedexplorer.data.local.catimage.CatImageEntity
import com.challenge.catbreedexplorer.data.local.catimage.toDomain
import com.challenge.catbreedexplorer.data.local.catimage.toEntity
import com.challenge.catbreedexplorer.data.remote.ApiService
import com.challenge.catbreedexplorer.data.remote.catimage.toDomain
import com.challenge.catbreedexplorer.domain.model.CatImage
import com.challenge.catbreedexplorer.domain.repository.CatImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CatImageRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val catImageDao: CatImageDao,
) : CatImageRepository {

    override suspend fun refreshCatImages(breedId: String) {
        val apiResponse = apiService.getCatImages(breedId)
        if (apiResponse.isSuccessful) {
            apiResponse.body()?.let { catImagesDto ->
                val catImages = catImagesDto.map { it.toDomain() }
                val entities: List<CatImageEntity> = catImages.map { it.toEntity(breedId) }
                catImageDao.clearAllCatImage(breedId) // Clear old data
                catImageDao.insertCatImages(entities) // Save new data
            }
        } else {
            throw Exception("Failed to fetch data from API")
        }
    }

    override fun getImagesForBreed(breedId: String): Flow<List<CatImage>> {
        return catImageDao.getImagesForBreed(breedId)
            .catch { emit(emptyList()) }
            .map { entities ->
                entities.map { it.toDomain() }
            }
    }

}