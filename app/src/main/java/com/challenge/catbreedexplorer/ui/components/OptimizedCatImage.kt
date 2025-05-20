package com.challenge.catbreedexplorer.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter

@Composable
fun OptimizedCatImage(
    imageUrl: String,
    contentDescription: String = "Cat Image",
    modifier: Modifier = Modifier
) {
    // Using Coil's AsyncImage for optimized image loading
    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop, // Adjust the scaling as needed
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1.0f),
       //todo so, placeholder = painterResource(id = R.drawable.placeholder), // Placeholder while loading
       //todo so, error = painterResource(id = R.drawable.error_placeholder)  // Error image if failed
    )
}

