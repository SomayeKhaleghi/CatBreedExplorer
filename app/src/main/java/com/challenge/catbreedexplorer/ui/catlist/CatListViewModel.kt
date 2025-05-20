// CatListViewModel.kt
package com.challenge.catbreedexplorer.ui.catlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.catbreedexplorer.domain.repository.CatBreedRepository
import com.challenge.catbreedexplorer.domain.model.CatBreed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatListViewModel @Inject constructor(
    private val repository: CatBreedRepository
) : ViewModel() {

    // StateFlow to manage UI state (Immutable StateFlow)
    private val _state = MutableStateFlow<CatListState>(CatListState.Loading)
    val state: StateFlow<CatListState> = _state.asStateFlow()

    // Cached List for Search
    private var allCatBreeds: List<CatBreed> = emptyList()

    init {
        refreshCatBreeds()
    }

    /**
     * Handles user intents for Cat List screen.
     */
    fun handleIntent(intent: CatListIntent) {
        when (intent) {
            is CatListIntent.SearchCats -> searchCats(intent.query)
            is CatListIntent.RefreshCats -> refreshCatBreeds()
        }
    }

    /**
     * Refreshes the cat breeds from API and stores them in the database.
     */
     private  fun refreshCatBreeds() {
        viewModelScope.launch {
            _state.value = CatListState.Loading
            try {
                // Refresh data from API and save to DB
                repository.refreshCatBreeds()

                // Fetch from database (which now has fresh data)
                repository.getCatBreeds().collect { cats ->
                    allCatBreeds = cats // Cache the list for searching
                    _state.value = if (cats.isNotEmpty()) {
                        CatListState.Success(cats)
                    } else {
                        CatListState.Empty
                    }
                }
            } catch (e: Exception) {
                _state.value = CatListState.Error(e.message ?: "Failed to refresh data.")
            }
        }
    }

    /**
     * Searches for cats based on the query (real-time search & Filtering Existing List)
     */
    private fun searchCats(query: String) {
        if (query.isBlank()) {
            _state.value = CatListState.Success(allCatBreeds) // Show all if query is blank
            return
        }

        viewModelScope.launch {
            val currentState = _state.value
            if (currentState is CatListState.Success) {
                val filteredCats = currentState.cats.filter {
                    it.name.contains(query, ignoreCase = true)
                }
                _state.value = if (filteredCats.isEmpty()) {
                    CatListState.Empty
                } else {
                    CatListState.Success(filteredCats)
                }
            }

        }
    }
}