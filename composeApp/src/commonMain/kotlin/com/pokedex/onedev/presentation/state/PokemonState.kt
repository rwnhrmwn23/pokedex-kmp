package com.pokedex.onedev.presentation.state

import com.onedev.pokedex.domain.model.Pokemon

data class PokemonListUiState(
    val pokemonList: List<Pokemon> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isLoadingMore: Boolean = false
)