package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {

                val windowClass = calculateWindowSizeClass(activity = this)

                // Initialize NavHostController
                val navController = rememberNavController()

                // Create a NavHost for navigation
                NavHost(
                    navController = navController,
                    startDestination = "profile"
                ) {
                    composable("profile") {
                        // Composable pour le profil
                        Profil(navController, windowClass)
                    }
                    composable("films") {
                        // Composable pour l'écran des films
                        ScreenFilms(navController, windowClass)
                    }
                    composable("series") {
                        // Composable pour l'écran des séries
                        ScreenSeries(navController, windowClass)
                    }
                    composable("actors") {
                        // Composable pour l'écran des séries
                        ScreenActors(navController, windowClass)
                    }
                    composable(
                        route = "detailsActor/{actorID}",
                        arguments = listOf(navArgument("actorID") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val actorID = backStackEntry.arguments?.getString("actorID") ?: ""
                        DetailsActor(navController, actorID)
                    }
                    composable(
                        "detailsFilm/{filmID}",
                        arguments = listOf(navArgument("filmID") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val filmID = backStackEntry.arguments?.getString("filmID") ?: ""
                        DetailsFilm(navController, filmID)
                    }
                    composable(
                        "detailsSerie/{serieID}",
                        arguments = listOf(navArgument("serieID") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val serieID = backStackEntry.arguments?.getString("serieID") ?: ""
                        DetailsSerie(navController, serieID)
                    }
                }
            }
        }
    }
}


