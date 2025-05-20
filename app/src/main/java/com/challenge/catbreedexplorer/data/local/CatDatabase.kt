package com.challenge.catbreedexplorer.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CatBreedEntity::class], version = 5, exportSchema = false)
abstract class CatDatabase : RoomDatabase() {
    abstract fun catBreedDao(): CatBreedDao

    companion object {
        @Volatile
        private var INSTANCE: CatDatabase? = null

        fun getInstance(context: Context): CatDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CatDatabase::class.java,
                    "cat_breeds_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
