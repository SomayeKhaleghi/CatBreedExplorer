package com.challenge.catbreedexplorer.data.local.catimage

import com.challenge.catbreedexplorer.domain.model.CatImage

fun CatImageEntity.toDomain(): CatImage = CatImage(
    id = id,
    url = url,
    width = width,
    height = height
)

fun CatImage.toEntity(breedId: String): CatImageEntity = CatImageEntity(
    id = id,
    url = url,
    width = width,
    height = height,
    breedId = breedId
)
