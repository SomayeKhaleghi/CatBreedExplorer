package com.challenge.catbreedexplorer.model

import com.google.gson.annotations.SerializedName

data class CatBreed(
    val id: String,
    val name: String,
    val description: String,
    val lifeSpan: String,
    val temperament: String,
    val origin: String,
    val wikipediaUrl: String?,
    val imageUrl: String?
)