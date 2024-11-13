package com.example.myapplication

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import androidx.lifecycle.SavedStateHandle

class MainViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    val apikey ="317519a83cc36ab9367ba50e5aa75b40"

    val movies = MutableStateFlow<List<Film>>(listOf())
    val series = MutableStateFlow<List<Serie>>(listOf())
    val actors = MutableStateFlow<List<Actor>>(listOf())
    val collections = MutableStateFlow<List<Collection>>(listOf())
    val detailsFilm = MutableStateFlow<DetailsFilm>(DetailsFilm())
    val detailsSerie = MutableStateFlow<DetailsSerie>(DetailsSerie())
    val detailsActor = MutableStateFlow<DetailsActor>(DetailsActor())
    private val filmID: String? = savedStateHandle["filmID"]
    private val serieID: String? = savedStateHandle["serieID"]
    private val actorID: String? = savedStateHandle["actorID"]

    val service = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(tmdbAPI::class.java)

    fun getTrendingMovies() {
        viewModelScope.launch {
            movies.value = service.getTrendingMovies(apikey, "fr").results
        }
    }

    fun getTrendingSeries() {
        viewModelScope.launch {
            series.value = service.getTrendingSeries(apikey, "fr").results
        }
    }

    fun getTrendingActors() {
        viewModelScope.launch {
            actors.value = service.getTrendingActors(apikey, "fr").results
        }
    }

    fun getFilmDetails() {
        viewModelScope.launch {
            detailsFilm.value = service.getFilmDetails(filmID ?: "", apikey, "fr")
        }
    }

    fun getSerieDetails() {
        viewModelScope.launch {
            detailsSerie.value = service.getSerieDetails(serieID ?: "", apikey, "fr")
        }
    }

    fun getActorDetails() {
        viewModelScope.launch {
            detailsActor.value = service.getActorDetails(actorID ?: "", apikey, "fr")
        }
    }

    fun searchMovie(query: String) {
        viewModelScope.launch {
            movies.value = service.searchMovie(apikey, query).results
        }
    }

    fun searchSerie(query: String) {
        viewModelScope.launch {
            series.value = service.searchSerie(apikey, query).results
        }
    }

    fun searchActor(query: String) {
        viewModelScope.launch {
            actors.value = service.searchActor(apikey, query).results
        }
    }

    fun getCollection(){
        viewModelScope.launch{
            collections.value = service.getCollection(apikey, "fr","horror").results
        }
    }

}