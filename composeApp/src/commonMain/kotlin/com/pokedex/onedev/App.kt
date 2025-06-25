package com.pokedex.onedev

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.pokedex.onedev.presentation.ui.PokemonListScreen
import com.pokedex.onedev.presentation.viewmodel.PokemonViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.getKoin

@Composable
@Preview
fun App() {
    val viewModel: PokemonViewModel = getKoin().get()
    MaterialTheme {
        PokemonListScreen(viewModel)
    }
}