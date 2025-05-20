package com.challenge.catbreedexplorer.data.repository

import com.challenge.catbreedexplorer.data.local.catbreed.CatBreedDao
import com.challenge.catbreedexplorer.data.local.catbreed.toDomain
import com.challenge.catbreedexplorer.data.remote.ApiService
import com.challenge.catbreedexplorer.domain.model.CatBreed
import com.challenge.catbreedexplorer.domain.repository.CatDetailRepository
import javax.inject.Inject

class CatDetailRepositoryImpl @Inject constructor(
    private val dao: CatBreedDao,
    private val apiService: ApiService
) : CatDetailRepository {

    override suspend fun getBreedById(id: String): CatBreed? {
        return dao.getCatBreedById(id)?.toDomain()
    }
}