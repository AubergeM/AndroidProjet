package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNaviguationBar(navController: NavController) {
    val mainViewModel: MainViewModel = viewModel()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    var searchText by remember { mutableStateOf("") } // Texte de recherche
    var searchBarVisible by remember { mutableStateOf(false) } // État pour indiquer si la barre de recherche est visible

    if (!searchBarVisible) {
        TopAppBar(
            title = { Text(text = "Malaury's TV", color = Color.White) },
            colors = TopAppBarDefaults.topAppBarColors(Color(0xFF800080)),
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
            },
            actions = {
                IconButton(onClick = { searchBarVisible = true }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = Color.White)
                }
            }
        )
    } else {
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(40.dp)),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFF800080),
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.White,
                cursorColor = Color.White
            ),
            textStyle = TextStyle(color = Color.White),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    when (currentDestination?.route) {
                        "films" -> mainViewModel.searchMovie(searchText)
                        "series" -> mainViewModel.searchSerie(searchText)
                        "actors" -> mainViewModel.searchActor(searchText)
                    }
                    searchBarVisible = false
                }
            ),
            leadingIcon = {
                IconButton(onClick = { searchBarVisible = false }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = { searchText = "" }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear",
                        tint = Color.White
                    )
                }
            }
        )

    }
}

@Composable
fun BottomNaviguationBar(navController: NavController) {
    BottomAppBar(containerColor = Color(0xFF800080)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                IconButton(onClick = { navController.navigate("profile")}, modifier = Modifier.weight(1f).size(50.dp)){
                    Column (horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painterResource(R.drawable.home_logo),
                            contentDescription = "",
                            modifier = Modifier
                                .size(30.dp) ,
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    Text(text = "Profil", color = Color.White)
                }
            }
                IconButton(onClick = { navController.navigate("films")}, modifier = Modifier.weight(1f).size(50.dp)){
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painterResource(R.drawable.movie_logo),
                            contentDescription = "",
                            modifier = Modifier
                                .size(30.dp) ,
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    Text(text = "Films", color = Color.White)
                }
            }
                IconButton(onClick = { navController.navigate("series") }, modifier = Modifier.weight(1f).size(50.dp)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painterResource(R.drawable.tv_logo),
                            contentDescription = "",
                            modifier = Modifier
                                .size(30.dp),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    Text(text = "Séries", color = Color.White)
                }
            }
                IconButton(onClick = { navController.navigate("actors") }, modifier = Modifier.weight(1f).size(50.dp)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painterResource(R.drawable.acteur_logo),
                            contentDescription = "",
                            modifier = Modifier
                                .size(30.dp),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    Text(text = "Acteurs", color = Color.White)
                }
            }
        }
    }
}

/*@Composable
fun FloatButton(navController: NavController){
    FloatingActionButton(
        contentColor = Color(0xFF800080),
        onClick = { navController.navigateUp() },
        modifier = Modifier
            .size(30.dp)
            .padding(16.dp)
            .clip(CircleShape)
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = "Search",
            tint = Color.White,
        )
    }
}*/

@Composable
fun SideNaviguationBar(navController: NavController) {//doit s'afficher en format paysage
    NavigationRail(contentColor = Color(0xFF800080)) {
            NavigationRailItem(
                icon = {
                    Image(
                        painterResource(R.drawable.home_logo),
                        contentDescription = "Profil",
                        modifier = Modifier.size(30.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                },
                label = { Text("Profil", color = Color.White) },
                selected = false,
                onClick = { navController.navigate("profile") }
            )
            NavigationRailItem(
                icon = {
                    Image(
                        painterResource(R.drawable.movie_logo),
                        contentDescription = "Films",
                        modifier = Modifier.size(30.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                },
                label = { Text("Films", color = Color.White) },
                selected = false,
                onClick = { navController.navigate("films") }
            )

            NavigationRailItem(
                icon = {
                    Image(
                        painterResource(R.drawable.tv_logo),
                        contentDescription = "Séries",
                        modifier = Modifier.size(30.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                },
                label = { Text("Séries", color = Color.White) },
                selected = false,
                onClick = { navController.navigate("series") }
            )

            NavigationRailItem(
                icon = {
                    Image(
                        painterResource(R.drawable.acteur_logo),
                        contentDescription = "Acteurs",
                        modifier = Modifier.size(30.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                },
                label = { Text("Acteurs", color = Color.White) },
                selected = false,
                onClick = { navController.navigate("actors") }
            )
        }
    }




