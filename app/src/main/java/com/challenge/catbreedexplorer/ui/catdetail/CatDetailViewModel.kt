package com.challenge.catbreedexplorer.ui.catdetail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.catbreedexplorer.domain.repository.CatDetailRepository
import com.challenge.catbreedexplorer.domain.repository.CatImageRepository
import com.challenge.catbreedexplorer.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatDetailViewModel @Inject constructor(
    private val catDetailRepository: CatDetailRepository,
    private val catImageRepository: CatImageRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow<CatDetailState>(CatDetailState.Loading)
    val uiState: StateFlow<CatDetailState> = _uiState.asStateFlow()

    fun loadDetails(breedId: String) {
        viewModelScope.launch {
            try {
                _uiState.value = CatDetailState.Loading

                val breed = catDetailRepository.getBreedById(breedId)
                val useCache = !NetworkUtils.isNetworkAvailable(context)
                if (useCache) {
                    val cachedImages =
                        catImageRepository.getImagesForBreed(breedId).firstOrNull() ?: emptyList()
                    if (breed != null) {
                        _uiState.value = CatDetailState.Success(breed, cachedImages)
                    } else {
                        _uiState.value =
                            CatDetailState.Error("Offline and no cached data available.")
                    }
                    return@launch
                }

                try {
                    catImageRepository.refreshCatImages(breedId)
                    val images =
                        catImageRepository.getImagesForBreed(breedId).firstOrNull() ?: emptyList()
                    if (breed != null) {
                        _uiState.value = CatDetailState.Success(breed, images)
                    } else {
                        _uiState.value = CatDetailState.Empty
                    }

                } catch (e: Exception) {
                    if (breed != null) {
                        val cachedImages =
                            catImageRepository.getImagesForBreed(breedId).firstOrNull()
                                ?: emptyList()
                        _uiState.value = CatDetailState.Success(breed, cachedImages)
                    } else {
                        _uiState.value =
                            CatDetailState.Error("Something went wrong. Offline and no cache.")
                    }
                }
            }
            catch (e: Exception) {
                CatDetailState.Error("Something went wrong. Offline and no cache.")
            }
        }
    }
}