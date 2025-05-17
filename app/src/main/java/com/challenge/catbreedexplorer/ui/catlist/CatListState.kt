package com.challenge.catbreedexplorer.ui.catlist

data class CatListState(
    val isLoading: Boolean = false,
    val cats: List<String> = emptyList(),
    val error: String? = null
)