package com.challenge.catbreedexplorer.ui.catlist

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.challenge.catbreedexplorer.domain.model.CatBreed
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
                    description= "The Abyssinian is easy to care for, and a joy to have in your home. They’re affectionate cats and love both people and other animals.",
                    lifeSpan = "14 - 15",
                    temperament = "Active, Energetic, Independent, Intelligent, Gentle",
                    origin = "Egypt",
                    wikipediaUrl = "https://en.wikipedia.org/wiki/Abyssinian_(cat)",
                    imageUrl = "https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg"
                ),
                CatBreed(
                    id = "2",
                    name = "Aegean",
                    description= "Native to the Greek islands known as the Cyclades in the Aegean Sea, these are natural cats, meaning they developed without humans getting involved in their breeding. As a breed, Aegean Cats are rare, although they are numerous on their home islands. They are generally friendly toward people and can be excellent cats for families with children.",
                    lifeSpan = "5 - 9",
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

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun rememberFakeCatListViewModel(): FakeCatListViewModel {
    return FakeCatListViewModel()
}