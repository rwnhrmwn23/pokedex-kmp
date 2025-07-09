package com.pokedex.onedev.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    object Pokemon

    @Serializable
    data class PokemonDetail(val name: String)
}

