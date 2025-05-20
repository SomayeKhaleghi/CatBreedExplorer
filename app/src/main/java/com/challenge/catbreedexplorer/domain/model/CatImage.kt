package com.challenge.catbreedexplorer.domain.model

import com.google.gson.annotations.SerializedName

data class CatImage(
    val id: String,
    val url: String,
    val width: Int,
    val height: Int
)