package com.challenge.catbreedexplorer.data.remote

import com.google.gson.annotations.SerializedName

//      DTO stands for Data Transfer Object.
//      It is a design pattern used to transfer data between different parts of an application,
//      typically between:
//          1- Network (API) and App (ViewModel, UI).
//          2- Database and App (Repository, Use Case).

data class CatBreedDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("temperament") val temperament: String,
    @SerializedName("origin") val origin: String,
    @SerializedName( "wikipedia_url") val wikipediaUrl: String?,
    @SerializedName("image") val image: ImageDto?
) {
    data class ImageDto(
        @SerializedName("id") val id: String?,
        @SerializedName("url") val url: String?
    )
}