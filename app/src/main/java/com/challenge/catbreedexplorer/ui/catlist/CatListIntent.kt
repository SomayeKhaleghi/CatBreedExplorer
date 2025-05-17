package com.challenge.catbreedexplorer.ui.catlist

sealed class CatListIntent {
    object LoadCatList : CatListIntent()
    data class SearchCat(val query: String) : CatListIntent()
}