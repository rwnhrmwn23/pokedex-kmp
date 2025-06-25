package com.pokedex.onedev.presentation.state

import com.onedev.pokedex.domain.model.Pokemon
import com.onedev.pokedex.domain.model.PokemonDetail

sealed class PokemonState {
    data object Idle: PokemonState()
    data object Loading: PokemonState()
    data class ListLoaded(val data: List<Pokemon>): PokemonState()
    data class DetailLoaded(val data: PokemonDetail): PokemonState()
    data class Error(val message: String): PokemonState()
}