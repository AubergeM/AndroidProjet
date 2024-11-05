package com.example.myapplication
import androidx.compose.foundation.layout.padding
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
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
fun ScreenFilms(
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
            val paddingModifier = if (isCompact) Modifier.padding(top = 60.dp) else Modifier.padding(start = 68.dp)
            val columns = if (isCompact) 2 else 4
            Films(navController, mainViewModel, columns, paddingModifier)
        }
    }
}

@Composable
fun Films(
    navController: NavController, viewModel: MainViewModel, columns: Int, modifier: Modifier
) {
    val movies by viewModel.movies.collectAsState()

    if (movies.isEmpty()) {
        viewModel.getTrendingMovies()
    }
    if (movies.isNotEmpty()) {
        LazyVerticalGrid(columns = GridCells.Fixed(columns), modifier = modifier.fillMaxSize()) {
            items(movies){ film ->
                ScreenFilm(film, navController)
            }
        }
    }
}

@Composable
fun ScreenFilm(film: Film, navController: NavController) {

    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(385.dp)
            .clickable { navController.navigate("detailsFilm/${film.id}") }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Image(
                painter = rememberImagePainter(
                    data = "https://image.tmdb.org/t/p/w780" + film.poster_path,
                    builder = { crossfade(true) }
                ),
                contentDescription = "Image film ${film.title}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )

            Text(
                text = film.title,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(top = 10.dp)
            )

            Text(
                text = formatDate(
                    film.release_date,
                    "yyyy-MM-dd",
                    "dd MMM yyyy",
                    Locale.FRANCE
                )

            )
        }
    }
}

fun formatDate(
    inputDate: String,
    inputDateFormat: String,
    outputDateFormat: String,
    locale: Locale
): String {
    val inputFormat = SimpleDateFormat(inputDateFormat, locale)
    val outputFormat = SimpleDateFormat(outputDateFormat, locale)
    Log.d("date", inputDate)
    Log.d("date", inputFormat.toString())
    val date = inputFormat.parse(inputDate)
    return outputFormat.format(date)
}

