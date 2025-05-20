package com.challenge.catbreedexplorer.domain.repository

import com.challenge.catbreedexplorer.domain.model.CatBreed
import kotlinx.coroutines.flow.Flow

interface CatBreedRepository {
    suspend fun refreshCatBreeds()
    fun getCatBreeds(): Flow<List<CatBreed>>
}