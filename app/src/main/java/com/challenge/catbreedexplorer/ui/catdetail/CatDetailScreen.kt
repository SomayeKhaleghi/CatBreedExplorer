package com.challenge.catbreedexplorer.ui.catdetail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CatDetailScreen(breedId: String) {
    // You can call viewModel.loadCatDetail(catId) here
    Text("Details for Cat ID: $breedId")
}