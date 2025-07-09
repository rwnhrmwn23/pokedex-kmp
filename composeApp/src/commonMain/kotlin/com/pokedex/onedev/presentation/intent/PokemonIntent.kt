package com.pokedex.onedev.presentation.intent

sealed class PokemonIntent {
    data object LoadInitial: PokemonIntent()
    data object LoadMorePokemon: PokemonIntent()
    data class Search(val query: String): PokemonIntent()
}