package com.example.myapplication

import androidx.compose.foundation.layout.padding
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter


@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScreenActors(
    navController: NavController,
    windowClass: WindowSizeClass
){
    val mainViewModel: MainViewModel = viewModel()
    val isCompact = windowClass.widthSizeClass == WindowWidthSizeClass.Compact

    Scaffold(
        topBar = { TopNaviguationBar(navController) },
        bottomBar = { if (isCompact) BottomNaviguationBar(navController) else null }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFEE82EE),
        ) {
            val paddingModifier = if (isCompact) Modifier.padding(top = 60.dp, bottom = 60.dp) else Modifier.padding(start = 68.dp)
            val columns = if (isCompact) 2 else 4
            Actors(navController, mainViewModel, columns, paddingModifier)
        }
    }
}

@Composable
fun Actors(
    navController: NavController, viewModel: MainViewModel, columns: Int, modifier: Modifier
) {
    val actors by viewModel.actors.collectAsState()

    if (actors.isEmpty()) {
        viewModel.getTrendingActors()
    }
    if (actors.isNotEmpty()) {
        LazyVerticalGrid(columns = GridCells.Fixed(columns), modifier = modifier.fillMaxSize()) {
            items(actors){ actor ->
                ScreenActor(actor, navController)
            }
        }
    }
}

@Composable
fun ScreenActor(actor: Actor, navController: NavController) {

    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(385.dp)
            .clickable { navController.navigate("detailsActor/${actor.id}") }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Image(
                painter = rememberImagePainter(
                    data = "https://image.tmdb.org/t/p/w780" + actor.profile_path,
                    builder = { crossfade(true) }
                ),
                contentDescription = "Image film ${actor.name}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )

            Text(
                text = actor.name,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(top = 10.dp)
            )
        }
    }
}



