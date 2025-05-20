package com.challenge.catbreedexplorer.data.remote.catimage

import com.challenge.catbreedexplorer.domain.model.CatImage

fun CatImageDto.toDomain(): CatImage = CatImage(
    id = id,
    url = url,
    width = width,
    height = height
)

