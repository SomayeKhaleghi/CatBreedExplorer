package com.challenge.catbreedexplorer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CatBreedEntity::class], version = 7, exportSchema = false)
abstract class CatDatabase : RoomDatabase() {
    abstract fun catBreedDao(): CatBreedDao
}
