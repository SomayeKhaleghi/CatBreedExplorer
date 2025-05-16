package com.challenge.catbreedexplorer.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CatListScreen() {
    val fakeCats = listOf(
        "Persian Cat",
        "Siamese Cat",
        "Maine Coon",
        "Scottish Fold",
        "Bengal Cat"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(fakeCats.size) { index ->
            Text(
                text = fakeCats[index],
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CatListScreenPreview() {
    CatListScreen()
}