package com.challenge.catbreedexplorer.ui.catlist

import com.challenge.catbreedexplorer.domain.model.CatBreed

sealed class CatListState {
    object Loading : CatListState()
    data class Success(val cats: List<CatBreed>) : CatListState()
    data class Error(val message: String) : CatListState()
    object Empty : CatListState()
}