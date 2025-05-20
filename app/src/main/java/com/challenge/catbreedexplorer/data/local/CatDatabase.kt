package com.challenge.catbreedexplorer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.challenge.catbreedexplorer.data.local.catbreed.CatBreedDao
import com.challenge.catbreedexplorer.data.local.catbreed.CatBreedEntity
import com.challenge.catbreedexplorer.data.local.catimage.CatImageDao
import com.challenge.catbreedexplorer.data.local.catimage.CatImageEntity

@Database(entities = [CatBreedEntity::class, CatImageEntity::class], version = 10, exportSchema = false)
abstract class CatDatabase : RoomDatabase() {
    abstract fun catBreedDao(): CatBreedDao
    abstract fun catImageDao(): CatImageDao
}
