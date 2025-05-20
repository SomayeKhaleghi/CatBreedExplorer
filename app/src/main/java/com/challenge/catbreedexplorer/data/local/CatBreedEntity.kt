package com.challenge.catbreedexplorer.data.local


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "cat_breeds")
data class CatBreedEntity(
    @PrimaryKey(autoGenerate = true)
    val localId: Int = 0,
    val id: String,
    val name: String,
    val temperament: String,
    val origin: String,
    val wikipediaUrl: String?,
    val imageUrl: String?
)