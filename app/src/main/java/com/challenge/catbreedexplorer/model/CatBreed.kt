package com.challenge.catbreedexplorer.model

data class CatBreed(
    val id: String,
    val name: String,
    val temperament: String,
    val origin: String,
    val wikipediaUrl: String?,
    val imageUrl: String?
)