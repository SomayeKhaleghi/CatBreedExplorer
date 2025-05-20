package com.challenge.catbreedexplorer.data.remote

import com.challenge.catbreedexplorer.model.CatBreed

fun CatBreedDto.toDomain(): CatBreed {
    return CatBreed(
        id = this.id,
        name = this.name,
        temperament = this.temperament,
        origin = this.origin,
        imageUrl = this.image?.url,
        wikipediaUrl = this.wikipediaUrl
    )
}

fun CatBreed.toDto(): CatBreedDto {
    return CatBreedDto(
        id = this.id,
        name = this.name,
        temperament = this.temperament,
        origin = this.origin,
        wikipediaUrl = this.wikipediaUrl,
        image = if (this.imageUrl != null) {
            CatBreedDto.ImageDto(
                id = null, // Since your domain model doesn't have an image ID
                url = this.imageUrl
            )
        } else {
            null
        }
    )
}

