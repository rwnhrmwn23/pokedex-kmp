package com.pokedex.onedev.presentation.intent

sealed class PokemonDetailIntent {
    data class LoadDetail(val name: String) : PokemonDetailIntent()
}