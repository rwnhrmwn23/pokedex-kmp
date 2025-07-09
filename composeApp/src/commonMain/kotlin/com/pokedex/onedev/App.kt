package com.pokedex.onedev

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.pokedex.onedev.presentation.navigation.AppNavHost
import com.pokedex.onedev.presentation.viewmodel.PokemonDetailViewModel
import com.pokedex.onedev.presentation.viewmodel.PokemonViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.getKoin

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    val pokemonViewModel: PokemonViewModel = getKoin().get()
    val pokemonDetailViewModel: PokemonDetailViewModel = getKoin().get()
    AppNavHost(navController, pokemonViewModel, pokemonDetailViewModel)
}
