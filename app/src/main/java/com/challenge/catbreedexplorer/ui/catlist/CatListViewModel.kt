package com.challenge.catbreedexplorer.ui.catlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.catbreedexplorer.domain.model.CatBreed
import com.challenge.catbreedexplorer.domain.repository.CatBreedRepository
import com.challenge.catbreedexplorer.utils.NetworkChecker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatListViewModel @Inject constructor(
    private val repository: CatBreedRepository,
    private val networkChecker: NetworkChecker,
) : ViewModel() {

    private val _state = MutableStateFlow<CatListState>(CatListState.Loading)
    val state: StateFlow<CatListState> = _state.asStateFlow()

    private var allCatBreeds: List<CatBreed> = emptyList()

    /* init {
        refreshCatBreeds()
    }*/

    fun handleIntent(intent: CatListIntent) {
        when (intent) {
            is CatListIntent.SearchCats -> searchCats(intent.query)
            is CatListIntent.RefreshCats -> refreshCatBreeds()
        }
    }

    private fun refreshCatBreeds() {
        viewModelScope.launch {
            _state.value = CatListState.Loading
            try {
                try {
                    if (networkChecker.isOnline())
                        repository.refreshCatBreeds() // API + DB update
                } catch (e: Exception) {
                    _state.value = CatListState.Error(e.message ?: "Failed to refreshCatBreeds.")
                }

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

    private fun searchCats(query: String) {
        if (query.isBlank()) {
            _state.value = if (allCatBreeds.isNotEmpty()) {
                CatListState.Success(allCatBreeds)
            } else {
                CatListState.Empty
            }
            return
        }

        viewModelScope.launch {
            val filtered = allCatBreeds.filter {
                it.name.contains(query, ignoreCase = true)
            }
            _state.value = if (filtered.isEmpty()) {
                CatListState.Empty
            } else {
                CatListState.Success(filtered)
            }
        }
    }
}