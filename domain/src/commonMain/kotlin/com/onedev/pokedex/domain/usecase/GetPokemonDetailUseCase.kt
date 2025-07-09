package com.onedev.pokedex.domain.usecase

import com.onedev.pokedex.domain.repository.PokemonRepository

class GetPokemonDetailUseCase(private val repository: PokemonRepository) {
    suspend operator fun invoke(pokemonName: String) = repository.getPokemonDetail(pokemonName)
}