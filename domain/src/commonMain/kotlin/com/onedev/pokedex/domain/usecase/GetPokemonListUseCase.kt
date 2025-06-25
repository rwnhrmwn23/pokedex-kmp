package com.onedev.pokedex.domain.usecase

import com.onedev.pokedex.domain.repository.PokemonRepository

class GetPokemonListUseCase(private val repository: PokemonRepository) {
    suspend operator fun invoke(offset: Int, limit: Int) = repository.getPokemonList(offset, limit)
}