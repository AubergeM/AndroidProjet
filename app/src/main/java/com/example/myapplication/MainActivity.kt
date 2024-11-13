package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi

import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.window.core.layout.WindowWidthSizeClass
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.serialization.Serializable

@Serializable
class Profil

@Serializable
class Movies



class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val viewModel: MainViewModel = viewModel()
                val windowClass = calculateWindowSizeClass(activity = this)
                val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination


                Scaffold(
                    topBar = {
                        /*when (windowSizeClass.windowWidthSizeClass) {
                            WindowWidthSizeClass.COMPACT -> {*/
                                if (!(currentDestination?.route == "profile")) {
                                    TopNaviguationBar(navController)
                                }


                    },

                    bottomBar = {
                                if (!(currentDestination?.route == "profile")) {
                                    when (windowSizeClass.windowWidthSizeClass) {
                                        WindowWidthSizeClass.COMPACT -> {
                                    BottomNaviguationBar (navController)
                                }
                            }
                        }
                    },


                    )
                { innerPadding ->
                    Row() {//je sais pas ce qui ne fonctionne pas mais ça s'affiche en blanc
                        if (!(currentDestination?.route == "profile")) {
                            when (windowSizeClass.windowWidthSizeClass) {
                                WindowWidthSizeClass.COMPACT -> {
                                    // Code pour le cas COMPACT, s'il y en a
                                }
                                else -> {
                                    Column() {
                                        SideNaviguationBar(navController)
                                    }
                                }
                            }
                        }
                        Column {
                            NavHost(
                                navController = navController,
                                startDestination = "profile",
                                modifier = Modifier.padding(innerPadding),
                            ) {
                                composable("profile") {
                                    // Composable pour le profil
                                    Profil(navController, windowClass)
                                }
                                composable("films") {
                                    // Composable pour l'écran des films
                                    Films(navController, viewModel, windowClass)
                                }
                                composable("series") {
                                    // Composable pour l'écran des séries
                                    Series(navController, viewModel, windowClass)
                                }
                                composable("actors") {
                                    // Composable pour l'écran des séries
                                    Actors(navController, viewModel, windowClass)
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
        }
    }


}


