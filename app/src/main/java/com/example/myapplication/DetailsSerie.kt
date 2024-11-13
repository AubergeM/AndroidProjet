package com.example.myapplication

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import java.util.Locale
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items

@Composable
fun DetailsSerie(navController: NavController, serieID: String) {
    val mainViewModel: MainViewModel = viewModel()
    val detailsSerie by mainViewModel.detailsSerie.collectAsState()

    if (detailsSerie.name.isEmpty()) {
        mainViewModel.getSerieDetails()
    }

    if (detailsSerie.name.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEE82EE))
                .padding(16.dp)
        ) {
            item{
                DisplayBackdropImage(detailsSerie)
                DisplaySerieInfo(detailsSerie)
                DisplaySynopsis(detailsSerie)
                DisplayCast(navController, detailsSerie)
            }
        }
    }
}

@Composable
fun DisplayBackdropImage(detailsSerie: DetailsSerie) {
    Image(
        painter = rememberImagePainter(
            data = "https://image.tmdb.org/t/p/w1280${detailsSerie.backdrop_path}",
            builder = { crossfade(true) }
        ),
        contentDescription = "Image série ${detailsSerie.name}",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clip(RoundedCornerShape(8.dp))
    )
}

@Composable
fun DisplaySerieInfo(detailsSerie: DetailsSerie) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top,
        modifier = Modifier.padding(16.dp)
    ) {
        Image(
            painter = rememberImagePainter(
                data = "https://image.tmdb.org/t/p/w1280${detailsSerie.poster_path}",
                builder = { crossfade(true) }
            ),
            contentDescription = "Poster de ${detailsSerie.name}",
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(16.dp))
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Text(
                text = detailsSerie.name,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier.padding(vertical = 8.dp),
                color = Color(0xFF800080)
            )
            Text(
                text = formatDate(
                    detailsSerie.first_air_date,
                    "yyyy-dd-MM",
                    "dd MMM yyyy",
                    Locale.FRANCE
                ),
                fontSize = 18.sp
            )
            Text(
                text = getGenres(detailsSerie.genres),
                fontSize = 18.sp,
                fontStyle = FontStyle.Italic
            )
        }
    }
}

@Composable
fun DisplaySynopsis(serieDetails: DetailsSerie) {
    Text(
        text = "Synopsis",
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        modifier = Modifier.padding(bottom = 8.dp)
    )
        Text(
            text = serieDetails.overview,
            textAlign = TextAlign.Justify
        )
}

@Composable
fun DisplayCast(navController: NavController, detailsSerie: DetailsSerie) {
    if (detailsSerie.credits.cast.isNotEmpty()) {
        Text(
            text = "Têtes d'affiches",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyRow {
            items(detailsSerie.credits.cast.take(10)) { cast ->
                DisplayCastItem(navController, cast)
            }
        }
    }
}

@Composable
fun DisplayCastItem(navController: NavController, cast: Cast) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(end = 16.dp)
            .width(100.dp)
            .clickable { navController.navigate("ActorDetails/${cast.id}") }
    ) {
        Image(
            painter = rememberImagePainter(
                data = "https://image.tmdb.org/t/p/w780${cast.profile_path}",
                builder = { crossfade(true) }
            ),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Text(
            text = cast.name,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp)
        )
        Text(
            text = cast.character,
            fontSize = 10.sp,
            textAlign = TextAlign.Center
        )
    }
}

