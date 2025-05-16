package com.challenge.catbreedexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.challenge.catbreedexplorer.ui.CatListScreen
import com.challenge.catbreedexplorer.ui.theme.CatBreedExplorerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatBreedExplorerTheme {
                CatListScreen()
            }
        }
    }
}
