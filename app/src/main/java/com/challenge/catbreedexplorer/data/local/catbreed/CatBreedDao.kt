package com.challenge.catbreedexplorer.data.local.catbreed

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CatBreedDao {
    @Query("SELECT * FROM cat_breeds WHERE id = :id")
    suspend fun getCatBreedById(id: String): CatBreedEntity?

    @Query("SELECT * FROM cat_breeds")
    fun getAllCatBreeds(): Flow<List<CatBreedEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCatBreeds(catBreeds: List<CatBreedEntity>)

    @Query("DELETE FROM cat_breeds")
    suspend fun clearAllCatBreeds()
}