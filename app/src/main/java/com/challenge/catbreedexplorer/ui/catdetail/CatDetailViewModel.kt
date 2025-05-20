// CatListViewModel.kt
package com.challenge.catbreedexplorer.ui.catdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.catbreedexplorer.domain.repository.CatBreedRepository
import com.challenge.catbreedexplorer.domain.model.CatBreed
import com.challenge.catbreedexplorer.domain.repository.CatDetailRepository
import com.challenge.catbreedexplorer.domain.repository.CatImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatDetailViewModel @Inject constructor(
    private val catDetailRepo: CatDetailRepository,
    private val catImageRepo: CatImageRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CatDetailState>(CatDetailState.Loading)
    val uiState: StateFlow<CatDetailState> = _uiState.asStateFlow()

    fun loadDetails(breedId: String) {
        viewModelScope.launch {
            try {
                val breed = catDetailRepo.getBreedById(breedId)
                catImageRepo.refreshCatImages(breedId)

                if (breed != null) {
                    val images = catImageRepo.getImagesForBreed(breedId).first()
                    _uiState.value = CatDetailState.Success(breed, images)
                } else {
                    _uiState.value = CatDetailState.Error("Breed not found")
                }
            } catch (e: Exception) {
                _uiState.value = CatDetailState.Error(e.message ?: "Unknown error")
            }
        }
    }
}