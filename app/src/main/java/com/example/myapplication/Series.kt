package com.example.myapplication

import androidx.compose.foundation.layout.padding
import android.annotation.SuppressLint
import android.graphics.Movie

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import java.text.SimpleDateFormat
import java.util.Locale

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.LaunchedEffect
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
fun Series(
    navController: NavController, viewModel: MainViewModel, windowClass: WindowSizeClass
) {
    val series by viewModel.series.collectAsState()
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

    LaunchedEffect(key1 = true) {
        viewModel.getTrendingSeries()
    }

    when (windowSizeClass.windowWidthSizeClass) {
        androidx.window.core.layout.WindowWidthSizeClass.COMPACT ->{ val columns = 2
            LazyVerticalGrid(columns = GridCells.Fixed(columns), modifier = Modifier.fillMaxSize().background(Color(0xFFEE82EE))) {
                items(series){ serie ->
                    ScreenSerie(serie, navController)
                }
            }}
        else ->{
            val columns = 4
            LazyVerticalGrid(columns = GridCells.Fixed(columns), modifier = Modifier.fillMaxSize().background(Color(0xFFEE82EE))) {
                items(series){ serie ->
                    ScreenSerie(serie, navController)
                }
            }
        }
    }
}

@Composable
fun ScreenSerie(serie: Serie, navController: NavController) {

    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { navController.navigate("detailsSerie/${serie.id}") }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Image(
                painter = rememberImagePainter(
                    data = "https://image.tmdb.org/t/p/w780" + serie.poster_path,
                    builder = { crossfade(true) }
                ),
                contentDescription = "Image film ${serie.name}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )

            Text(
                text = serie.name,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(top = 10.dp)
            )
        }
    }
}



