package com.pokedex.onedev.presentation.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.pokedex.onedev.presentation.intent.PokemonIntent
import com.pokedex.onedev.presentation.state.PokemonState
import com.pokedex.onedev.presentation.viewmodel.PokemonViewModel

@Composable
fun PokemonListScreen(viewModel: PokemonViewModel) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.dispatch(PokemonIntent.LoadPokemonList)
    }

    when (state) {
        is PokemonState.Loading -> {
            Text("Loading...")
        }

        is PokemonState.ListLoaded -> {
            val list = (state as PokemonState.ListLoaded).data
            LazyColumn {
                items(list) { pokemon ->
                    Text(pokemon.name ?: "-")
                }
            }
        }

        is PokemonState.Error -> {
            val message = (state as PokemonState.Error).message
            Text("Error: $message")
        }

        PokemonState.Idle -> {}
        is PokemonState.DetailLoaded -> {}
    }
}
