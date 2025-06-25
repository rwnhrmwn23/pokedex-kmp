package com.onedev.pokedex.domain.model

data class PokemonDetail(
    val id: Int?,
    val name: String?,
    val height: Int?,
    val weight: Int?,
    val order: Int?,
    val types: List<PokemonType>?,
    val stats: List<PokemonStat>?,
    val abilities: List<PokemonAbility>?
)

data class PokemonType(
    val name: String?
)

data class PokemonStat(
    val name: String?,
    val baseStat: Int?,
)

data class PokemonAbility(
    val name: String?,
)
