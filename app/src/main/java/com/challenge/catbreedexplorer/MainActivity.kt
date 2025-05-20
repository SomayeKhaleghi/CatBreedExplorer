package com.challenge.catbreedexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.challenge.catbreedexplorer.ui.catlist.CatListScreen
import com.challenge.catbreedexplorer.ui.navigation.AppNavHost
import com.challenge.catbreedexplorer.ui.theme.CatBreedExplorerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            /*CatBreedExplorerTheme {
                CatListScreen()
            }*/

            val navController = rememberNavController()
            AppNavHost(navController = navController)
        }
    }
}
