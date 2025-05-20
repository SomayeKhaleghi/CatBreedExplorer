package com.challenge.catbreedexplorer.data.local.catimage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CatImageDao {

    @Query("SELECT * FROM cat_images WHERE breedId = :breedId")
    fun getImagesForBreed(breedId: String): Flow<List<CatImageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCatImages(images: List<CatImageEntity>)

    @Query("DELETE FROM cat_images WHERE breedId = :breedId")
    suspend fun clearAllCatImage(breedId: String)
}