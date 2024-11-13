package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Button

import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun Profil(
    navController: NavController,
    windowClass: WindowSizeClass
) {
    when (windowClass.widthSizeClass) {
        WindowWidthSizeClass.Compact ->
            Column(
                modifier = Modifier.fillMaxSize().background(Color(0xFFFFFFFF)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                PhotoProfil()
                Montexte()
                Spacer(modifier = Modifier.height(30.dp))
                MesContacts()
                Spacer(modifier = Modifier.height(20.dp))
                MonBouton(navController = navController)
            }

        else -> {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.background(Color(0xFFFFFFFF)).fillMaxSize()) {
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 20.dp)) {
                    Spacer(modifier = Modifier.height(10.dp))
                    PhotoProfil()
                    Montexte()
                }
                Spacer(modifier = Modifier.height(30.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 40.dp)
                ) {
                    MesContacts()
                    Spacer(modifier = Modifier.height(20.dp))
                    MonBouton(navController = navController)
                }
            }
        }
    }
}


@Composable
fun PhotoProfil(){
    Image(
        painterResource(R.drawable.chat),
        contentDescription = "un magnifique chat",
        contentScale = ContentScale.Crop,

        modifier = Modifier
            .size(200.dp)
            .clip(CircleShape)
            .paddingFromBaseline(top = 60.dp)
    )
}

@Composable
fun Montexte(){
    Text(text = "Malaury Auberge", fontSize = 30.sp)
    Text(text = "Etudiante en e-santé")
    Text(text = "INU Champollion - ISIS Castres", fontStyle = FontStyle.Italic)
}

@Composable
fun MesContacts(){
    Row {
        Image(
            painterResource(R.drawable.baseline_email_24),
            contentDescription = "un lettre",
            modifier = Modifier
                .size(20.dp)
        )
        Text(text = "aubergemalaury@gmail.com")
    }
    Row {
        Image(
            painterResource(R.drawable.linkedin_logo_transparent_free_png),
            contentDescription = "logo linkedin",
            modifier = Modifier
                .size(30.dp)
        )
        Text(text = "www.linkedin.com/in/malaury-auberge")
    }
}

@Composable
fun MonBouton(navController: NavController) {
    Button(onClick = { navController.navigate("films") },colors = ButtonDefaults.buttonColors( containerColor = Color(0xFF800080))) {
        Text(text = "Démarrer")
    }
}