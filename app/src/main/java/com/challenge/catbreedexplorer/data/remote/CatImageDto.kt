package com.challenge.catbreedexplorer.data.remote

import com.google.gson.annotations.SerializedName

data class CatImageDto(
    val id: String,
    val url: String,
    val width: Int,
    val height: Int
)