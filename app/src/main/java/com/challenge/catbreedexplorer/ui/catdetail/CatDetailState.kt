package com.challenge.catbreedexplorer.ui.catdetail

import com.challenge.catbreedexplorer.domain.model.CatBreed
import com.challenge.catbreedexplorer.domain.model.CatImage

sealed class CatDetailState {

    object Loading : CatDetailState()

    data class Success(
        val breed: CatBreed,
        val images: List<CatImage>
    ) : CatDetailState()

    data class Error(val message: String) : CatDetailState()

    object Empty : CatDetailState()
}
