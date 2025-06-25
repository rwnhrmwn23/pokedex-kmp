package com.pokedex.onedev.presentation.intent

sealed class PokemonIntent {
    data object LoadPokemonList: PokemonIntent()
    data class LoadPokemonDetail(val id: Int): PokemonIntent()
}