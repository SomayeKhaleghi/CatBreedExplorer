package com.challenge.catbreedexplorer.ui.catdetail

import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.challenge.catbreedexplorer.domain.model.CatBreed
import com.challenge.catbreedexplorer.domain.model.CatImage
import com.challenge.catbreedexplorer.ui.components.OptimizedCatImage

@Composable
fun CatDetailScreen(
    breedId: String,
    viewModel: CatDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()


    LaunchedEffect(breedId) {
        viewModel.loadDetails(breedId)
    }

    when (val state = uiState) {
        is CatDetailState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is CatDetailState.Success -> {
            CatDetailContent(breed = state.breed, images = state.images)
        }

        is CatDetailState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                Text(text = state.message ?: "Unknown error")
            }
        }

        CatDetailState.Empty -> {
            Text("Nothing to show.")
        }
    }
}

@Composable
fun CatDetailContent(breed: CatBreed, images: List<CatImage>) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = breed.name,
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Origin: ${breed.origin} \u2022 Life Span: ${breed.lifeSpan ?: "Unknown"}",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )

        Text(
            text = breed.temperament,
            style = MaterialTheme.typography.bodySmall,
            color = Color.DarkGray,
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = breed.description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        breed.wikipediaUrl?.let { url ->
            Button (
                onClick = {
                    val intent = CustomTabsIntent.Builder().build()
                    intent.launchUrl(context, Uri.parse(url))
                },
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text("Learn more on Wikipedia")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Images",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (images.isEmpty()) {
            Text("No images available.", color = Color.Gray)
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxHeight(),
                contentPadding = PaddingValues(4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(images) { image: CatImage ->

                   OptimizedCatImage(
                        imageUrl = image.url,
                        contentDescription = breed.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                    )
                }
            }
        }
    }
}