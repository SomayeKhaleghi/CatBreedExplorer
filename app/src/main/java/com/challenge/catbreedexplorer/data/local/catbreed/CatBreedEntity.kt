package com.challenge.catbreedexplorer.data.local.catbreed

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cat_breeds")
data class CatBreedEntity(
    @PrimaryKey(autoGenerate = true)
    val localId: Int = 0,
    val id: String,
    val name: String,
    val description: String,
    val lifeSpan: String,
    val temperament: String,
    val origin: String,
    val wikipediaUrl: String?,
    val imageUrl: String?
)