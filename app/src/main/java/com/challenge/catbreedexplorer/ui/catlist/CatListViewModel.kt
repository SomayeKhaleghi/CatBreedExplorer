// CatListViewModel.kt
package com.challenge.catbreedexplorer.ui.catlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.catbreedexplorer.data.CatRepository
import com.challenge.catbreedexplorer.data.remote.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatListViewModel @Inject constructor(
    private val repository: CatRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CatListState())
    val state: StateFlow<CatListState> = _state

    init {
        loadCatBreeds()
    }

    fun handleIntent(intent: CatListIntent) {
        when (intent) {
            is CatListIntent.LoadCatList -> loadCatBreeds()
            is CatListIntent.SearchCat -> searchCats(intent.query)
        }
    }

    private fun loadCatBreeds() {
        viewModelScope.launch {
            _state.value = CatListState(isLoading = true)
            try {
                val response = repository.getCatBreeds()
                if (response.isSuccessful) {
                    _state.value = CatListState(
                        isLoading = false,
                        cats = response.body()?.map { it.name } ?: emptyList(),
                        error = null
                    )
                } else {
                    _state.value = CatListState(error = "Failed to load cats")
                }
            } catch (e: Exception) {
                _state.value = CatListState(error = e.message)
            }
        }
    }

    private fun searchCats(query: String) {
        val filteredCats = state.value.cats.filter {
            it.contains(query, ignoreCase = true)
        }
        _state.value = state.value.copy(cats = filteredCats)
    }
}
