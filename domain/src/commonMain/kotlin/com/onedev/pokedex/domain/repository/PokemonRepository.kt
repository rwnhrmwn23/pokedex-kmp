package com.onedev.pokedex.domain.repository

import com.onedev.pokedex.domain.model.Pokemon
import com.onedev.pokedex.domain.model.PokemonDetail

interface PokemonRepository {
    suspend fun getPokemonList(offset: Int, limit: Int): List<Pokemon>
    suspend fun getPokemonDetail(pokemonName: String): PokemonDetail
}