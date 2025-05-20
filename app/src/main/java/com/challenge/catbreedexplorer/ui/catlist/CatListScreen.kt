package com.challenge.catbreedexplorer.ui.catlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.challenge.catbreedexplorer.model.CatBreed

@Composable
fun CatListScreen(viewModel: CatListViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    CatListScreenContent(
        state = state,
        onSearch = viewModel::searchCats,
        onRetry = viewModel::refreshCatBreeds
    )
}

// ✅ Main Content Composable for Reusability
@Composable
fun CatListScreenContent(
    state: CatListState,
    onSearch: (String) -> Unit,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SearchBar(onSearch = onSearch)

        Spacer(modifier = Modifier.height(16.dp))

        when (state) {
            is CatListState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            is CatListState.Error -> {
                ErrorView(message = state.message, onRetry = onRetry)
            }

            is CatListState.Empty -> {
                Text("No Cats Found", modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            is CatListState.Success -> {
                LazyColumn {
                    items(state.cats.size) { index ->
                        CatListItem(cat = state.cats[index])
                    }
                }
            }
        }
    }
}

// ✅ Search Bar for Searching Cats
@Composable
fun SearchBar(onSearch: (String) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }

    BasicTextField(
        value = searchQuery,
        onValueChange = {
            searchQuery = it
            onSearch(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(48.dp),
        textStyle = TextStyle(color = Color.Black),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 12.dp)
                    .fillMaxWidth()
            ) {
                if (searchQuery.isEmpty()) {
                    Text("Search Cats...", color = Color.Gray)
                }
                innerTextField()
            }
        }
    )
}

// ✅ Cat List Item with Optimized Image Loading
@Composable
fun CatListItem(cat: CatBreed) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        AsyncImage(
            model = cat.imageUrl,
            contentDescription = cat.name,
            modifier = Modifier.size(80.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(cat.name, style = MaterialTheme.typography.titleMedium)
            Text("Origin: ${cat.origin}", style = MaterialTheme.typography.bodySmall)
            Text("Temperament: ${cat.temperament}", style = MaterialTheme.typography.bodySmall)
        }
    }
}

// ✅ Error View for Better User Feedback
@Composable
fun ErrorView(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message, color = MaterialTheme.colorScheme.error)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

// ✅ Preview with Fake Data for Design Testing
@Preview(showBackground = true)
@Composable
fun CatListScreenPreview() {
    CatListScreenContent(
        state = CatListState.Success(
            listOf(
                CatBreed(
                    id = "1",
                    name = "Abyssinian",
                    temperament = "Active, Energetic, Independent, Intelligent, Gentle",
                    origin = "Egypt",
                    wikipediaUrl = "https://en.wikipedia.org/wiki/Abyssinian_(cat)",
                    imageUrl = "https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg"
                ),
                CatBreed(
                    id = "2",
                    name = "Aegean",
                    temperament = "Affectionate, Social, Intelligent, Playful, Active",
                    origin = "Greece",
                    wikipediaUrl = "https://en.wikipedia.org/wiki/Aegean_cat",
                    imageUrl = "https://cdn2.thecatapi.com/images/ozEvzdVM-.jpg"
                )
            )
        ),
        onSearch = {},
        onRetry = {}
    )
}
