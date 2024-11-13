package com.example.myapplication

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import java.util.Locale

@Composable
fun Collections(
    navController: NavController, viewModel: MainViewModel, windowClass: WindowSizeClass
) {
    val collections by viewModel.collections.collectAsState()
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

    LaunchedEffect(key1 = true) {
        viewModel.getCollection()
        //Log.d("Collections", "Collections: ${collections.size}")
    }

    when (windowSizeClass.windowWidthSizeClass) {
        androidx.window.core.layout.WindowWidthSizeClass.COMPACT ->{ val columns = 2
            LazyVerticalGrid(columns = GridCells.Fixed(columns), modifier = Modifier.fillMaxSize().background(
                Color(0xFFEE82EE)
            )) {

                items(collections){ collection ->
                    ScreenCollection(collection, navController)
                }
            }
        }
        else ->{
            val columns = 4
            LazyVerticalGrid(columns = GridCells.Fixed(columns), modifier = Modifier.fillMaxSize().background(
                Color(0xFFEE82EE)
            )) {
                items(collections){ collection ->
                    ScreenCollection(collection, navController)
                }
            }
        }
    }
}

@Composable
fun ScreenCollection(collection: Collection, navController: NavController) {

    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Image(
                painter = rememberImagePainter(
                    data = "https://image.tmdb.org/t/p/w780" + collection.poster_path,
                    builder = { crossfade(true) }
                ),
                contentDescription = "Image film ${collection.name}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )

            Text(
                text = collection.name,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(top = 10.dp)
            )

        }
    }
}



