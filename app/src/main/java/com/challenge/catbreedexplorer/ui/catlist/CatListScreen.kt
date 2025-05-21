package com.challenge.catbreedexplorer.ui.catlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.challenge.catbreedexplorer.domain.model.CatBreed
import com.challenge.catbreedexplorer.ui.components.OptimizedCatImage

@Composable
fun CatListScreen(
    viewModel: CatListViewModel = hiltViewModel(),
    onNavigateToDetail: (String) -> Unit,
) {
    val state by viewModel.state.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.handleIntent(CatListIntent.RefreshCats)
    }

    CatListScreenContent(
        state = state,
        onSearch = { query -> viewModel.handleIntent(CatListIntent.SearchCats(query)) },
        onRetry = { viewModel.handleIntent(CatListIntent.RefreshCats) },
        onCatClicked = { breedId -> onNavigateToDetail(breedId) }
    )
}

@Composable
fun CatListScreenContent(
    state: CatListState,
    onSearch: (String) -> Unit,
    onRetry: () -> Unit,
    onCatClicked: (String) -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search Field
        var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
        BasicTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                onSearch(it.text)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            singleLine = true,
            decorationBox = { innerTextField ->
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier.padding(8.dp)
                ) {
                    if (searchQuery.text.isEmpty()) {
                        Text("Search Cats...", color = Color.Gray)
                    }
                    innerTextField()
                }
            }
        )

        // Display State
        when (state) {
            is CatListState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is CatListState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(state.cats.size) { index ->
                        CatListItem(
                            cat = state.cats[index],
                            onClick = onCatClicked
                        )
                    }
                }
            }

            is CatListState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = state.message,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Red
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = onRetry) {
                            Text("Retry")
                        }
                    }
                }
            }

            is CatListState.Empty -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No cats found.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
fun CatListItem(cat: CatBreed, onClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick(cat.id) }
    ) {

        OptimizedCatImage(
            imageUrl = cat.imageUrl,
            contentDescription = cat.name,
            modifier = Modifier
                .size(80.dp)
                .padding(end = 16.dp),
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = cat.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = cat.origin,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Text(
                text = cat.temperament,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

/*@Preview(showBackground = true)
@Composable
fun CatListScreenPreview() {
    val fakeViewModel = rememberFakeCatListViewModel()
    val state by fakeViewModel.state.collectAsState()  // Correct way to observe StateFlow

    CatListScreenContent(
        state = state,
        onSearch = {},
        onRetry = {}
    )
}*/
