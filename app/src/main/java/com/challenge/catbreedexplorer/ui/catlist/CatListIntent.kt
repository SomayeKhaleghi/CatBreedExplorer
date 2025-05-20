package com.challenge.catbreedexplorer.ui.catlist

sealed class CatListIntent {
    data class SearchCats(val query: String) : CatListIntent()
    object RefreshCats : CatListIntent()
}