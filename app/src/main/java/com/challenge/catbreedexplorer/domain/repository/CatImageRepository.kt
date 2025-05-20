package com.challenge.catbreedexplorer.domain.repository

import com.challenge.catbreedexplorer.domain.model.CatImage
import kotlinx.coroutines.flow.Flow

interface CatImageRepository {

    suspend fun refreshCatImages(breedId: String)
    fun getImagesForBreed(breedId: String): Flow<List<CatImage>>
}
