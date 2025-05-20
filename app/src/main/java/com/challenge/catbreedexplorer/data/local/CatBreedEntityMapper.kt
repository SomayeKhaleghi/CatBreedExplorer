package com.challenge.catbreedexplorer.data.local

import com.challenge.catbreedexplorer.model.CatBreed
fun CatBreedEntity.toDomain(): CatBreed {
    return CatBreed(
        id = this.id,
        name = this.name,
        temperament = this.temperament,
        origin = this.origin,
        wikipediaUrl = this.wikipediaUrl,
        imageUrl = this.imageUrl
    )
}

fun CatBreed.toEntity(): CatBreedEntity {
    return CatBreedEntity(
        id = this.id,
        name = this.name,
        temperament = this.temperament,
        origin = this.origin,
        imageUrl = this.imageUrl,
        wikipediaUrl =   this.wikipediaUrl
    )
}
