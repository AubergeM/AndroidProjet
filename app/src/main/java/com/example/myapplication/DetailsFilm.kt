package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun DetailsFilm(navController: NavController, filmID: String) {
    val mainViewModel: MainViewModel = viewModel()
    val detailsFilm by mainViewModel.detailsFilm.collectAsState()

    if (detailsFilm.title.isEmpty()) {
        mainViewModel.getFilmDetails()
    }

    if (detailsFilm.title.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEE9898))
                .padding(16.dp)
        ) {
            item {
                BackdropImage(detailsFilm.backdrop_path)
                Spacer(modifier = Modifier.height(16.dp))
                MovieHeader(detailsFilm)
                Spacer(modifier = Modifier.height(16.dp))
                SynopsisSection(detailsFilm.overview)
                Spacer(modifier = Modifier.height(16.dp))
                CastSection(navController, detailsFilm.credits.cast)
            }
        }
    }
}

@Composable
fun BackdropImage(backdropPath: String) {
    Image(
        painter = rememberImagePainter(
            data = "https://image.tmdb.org/t/p/w1280$backdropPath",
            builder = { crossfade(true) }
        ),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(8.dp))
    )
}

@Composable
fun MovieHeader(detailsFilm: DetailsFilm) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = rememberImagePainter(
                data = "https://image.tmdb.org/t/p/w1280${detailsFilm.poster_path}",
                builder = { crossfade(true) }
            ),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = detailsFilm.title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = formatDate(detailsFilm.release_date, "yyyy-dd-MM", "dd MMM yyyy", Locale.FRANCE),
                fontSize = 14.sp
            )
            Text(
                text = getGenres(detailsFilm.genres),
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic
            )
        }
    }
}

@Composable
fun SynopsisSection(overview: String) {
    Text(
        text = "Synopsis",
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    Text(
        text = overview,
        textAlign = TextAlign.Justify
    )
}

@Composable
fun CastSection(navController: NavController, castList: List<Cast>) {
    if (castList.isNotEmpty()) {
        Text(
            text = "Têtes d'affiches",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyRow {
            items(castList.take(10)) { cast ->
                CastItem(navController, cast)
            }
        }
    }
}

@Composable
fun CastItem(navController: NavController, cast: Cast) {
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

// Helper function to format genres
fun getGenres(genres: List<Genre>): String {
    return genres.joinToString(", ") { it.name }
}

