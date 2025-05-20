package com.challenge.catbreedexplorer.ui.catlist

import androidx.lifecycle.ViewModel
import com.challenge.catbreedexplorer.model.CatBreed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeCatListViewModel : ViewModel() {

    // Initializing with a Success State containing mock data
    private val _state = MutableStateFlow<CatListState>(
        CatListState.Success(
            listOf(
                CatBreed(
                    id = "1",
                    name = "Abyssinian",
                    temperament = "Active, Energetic, Independent, Intelligent, Gentle",
                    origin = "Egypt",
                    wikipediaUrl = "https://en.wikipedia.org/wiki/Abyssinian_(cat)",
                    imageUrl = "https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg"
                ),
                CatBreed(
                    id = "2",
                    name = "Aegean",
                    temperament = "Affectionate, Social, Intelligent, Playful, Active",
                    origin = "Greece",
                    wikipediaUrl = "https://en.wikipedia.org/wiki/Aegean_cat",
                    imageUrl = "https://cdn2.thecatapi.com/images/ozEvzdVM-.jpg"
                )
            )
        )
    )

    val state: StateFlow<CatListState> = _state.asStateFlow()

    // Functions to change state for testing (optional)
    fun setLoading() {
        _state.value = CatListState.Loading
    }

    fun setError(message: String) {
        _state.value = CatListState.Error(message)
    }

    fun setSuccess(cats: List<CatBreed>) {
        _state.value = CatListState.Success(cats)
    }

    fun setEmpty() {
        _state.value = CatListState.Empty
    }
}
