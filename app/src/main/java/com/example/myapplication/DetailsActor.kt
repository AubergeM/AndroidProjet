package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import java.util.Locale

@Composable
fun DetailsActor(navController: NavController, actorID: String) {
    val mainViewModel: MainViewModel = viewModel()
    val detailsActor by mainViewModel.detailsActor.collectAsState()

    if (detailsActor.name.isEmpty()) mainViewModel.getActorDetails()

    if (detailsActor.name.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEE9898)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                HeaderSection(detailsActor)
                InfoBox(detailsActor)
                BiographySection(detailsActor.biography)
            }
        }
    }
}

@Composable
private fun HeaderSection(detailsActor: DetailsActor){
    Column(
        modifier = Modifier
            .padding(15.dp)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = detailsActor.name,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.padding(10.dp)
        )

        Image(
            painter = rememberImagePainter(
                data = "https://image.tmdb.org/t/p/h632${detailsActor.profile_path}",
                builder = { crossfade(true) }
            ),
            contentDescription = "Image acteur ${detailsActor.name}",
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .size(120.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
        )
    }
}

@Composable
private fun InfoBox(detailsActor: DetailsActor) {
    Column(
        modifier = Modifier
            .padding(15.dp)
            .background(Color.Black.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Sexe : ${if (detailsActor.gender == 1) "Femme" else "Homme"}", fontSize = 18.sp)

        detailsActor.known_for_department.takeIf { it.isNotEmpty() }?.let {
            Text(text = "Métier : $it", fontSize = 18.sp)
        }

        detailsActor.place_of_birth.takeIf { it.isNotEmpty() && detailsActor.birthday != null }?.let {
            Text(text = "Lieu de naissance : $it", fontSize = 18.sp)
            Text(
                text = "Anniversaire : ${formatDate(detailsActor.birthday, "yyyy-dd-MM", "dd MMM yyyy", Locale.FRANCE)}",
                fontSize = 18.sp
            )
        }
    }
}

@Composable
private fun BiographySection(biography: String) {
    if (biography.isNotEmpty()) {
        Column(modifier = Modifier.padding(15.dp)) {
            Text(
                text = "Biographie",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
            Text(
                text = biography,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(top = 15.dp)
            )
        }
    }
}
