package com.challenge.catbreedexplorer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.challenge.catbreedexplorer.ui.catlist.CatListScreen
import com.challenge.catbreedexplorer.ui.catdetail.CatDetailScreen

sealed class Screen(val route: String) {
    object CatList : Screen("cat_list")
    object CatDetail : Screen("cat_detail/{breedId}") {
        fun createRoute(breedId: String): String = "cat_detail/$breedId"
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = Screen.CatList.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.CatList.route) {
            CatListScreen(
                onNavigateToDetail = { breedId ->
                    navController.navigate(Screen.CatDetail.createRoute(breedId))
                }
            )
        }

        composable(
            route = Screen.CatDetail.route,
            arguments = listOf(navArgument("breedId") { defaultValue = "" })
        ) { backStackEntry ->
            val breedId = backStackEntry.arguments?.getString("breedId") ?: return@composable
            CatDetailScreen(breedId = breedId)
        }
    }
}