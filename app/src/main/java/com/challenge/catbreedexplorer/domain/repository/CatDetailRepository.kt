package com.challenge.catbreedexplorer.domain.repository

import com.challenge.catbreedexplorer.domain.model.CatBreed

interface CatDetailRepository {
    suspend fun getBreedById(id: String): CatBreed?
}