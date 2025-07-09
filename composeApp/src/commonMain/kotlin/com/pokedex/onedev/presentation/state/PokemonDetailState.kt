package com.pokedex.onedev.presentation.state

import com.onedev.pokedex.domain.model.PokemonDetail

sealed class PokemonDetailState {
    object Idle : PokemonDetailState()
    object Loading : PokemonDetailState()
    data class Success(val data: PokemonDetail) : PokemonDetailState()
    data class Error(val message: String) : PokemonDetailState()
}