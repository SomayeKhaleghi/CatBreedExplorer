package com.challenge.catbreedexplorer.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CatBreedEntity::class], version = 6, exportSchema = false)
abstract class CatDatabase : RoomDatabase() {
    abstract fun catBreedDao(): CatBreedDao
}
