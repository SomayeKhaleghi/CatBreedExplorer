// CatListScreen.kt
package com.challenge.catbreedexplorer.ui.catlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.challenge.catbreedexplorer.ui.theme.CatBreedExplorerTheme
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CatListScreen(viewModel: CatListViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header Section
        Text(
            text = "Cat List",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Search Field
        TextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                viewModel.handleIntent(CatListIntent.SearchCat(it))
            },
            placeholder = { Text("Search Cats...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Content Section
        when {
            state.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.fillMaxSize())
            }

            state.error != null -> {
                Text(
                    text = state.error ?: "Unknown error",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.fillMaxSize()
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.cats.size) { index ->
                        Text(
                            text = state.cats[index],
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }

        // Reload Button
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewModel.handleIntent(CatListIntent.LoadCatList) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Reload")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CatListScreenPreview() {
    CatBreedExplorerTheme {
        CatListScreen()
    }
}
