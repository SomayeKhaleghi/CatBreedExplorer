package com.challenge.catbreedexplorer.data.remote.catbreed

import com.challenge.catbreedexplorer.domain.model.CatBreed

fun CatBreedDto.toDomain(): CatBreed {
    return CatBreed(
        id = this.id,
        name = this.name,
        description = this.description,
        lifeSpan  = this.lifeSpan,
        temperament = this.temperament,
        origin = this.origin,
        imageUrl = this.image?.url,
        wikipediaUrl = this.wikipediaUrl
    )
}
