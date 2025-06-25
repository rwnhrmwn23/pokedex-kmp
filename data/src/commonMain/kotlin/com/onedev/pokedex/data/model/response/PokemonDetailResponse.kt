package com.onedev.pokedex.data.model.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetailResponse(
    @SerialName("abilities")
    val abilities: List<Ability?>? = null,
    @SerialName("height")
    val height: Int? = null,
    @SerialName("id")
    val id: Int? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("order")
    val order: Int? = null,
    @SerialName("stats")
    val stats: List<Stat?>? = null,
    @SerialName("types")
    val types: List<Type?>? = null,
    @SerialName("weight")
    val weight: Int? = null
) {
    @Serializable
    data class Ability(
        @SerialName("ability")
        val ability: Ability? = null,
    ) {
        @Serializable
        data class Ability(
            @SerialName("name")
            val name: String? = null,
        )
    }

    @Serializable
    data class Stat(
        @SerialName("base_stat")
        val baseStat: Int? = null,
        @SerialName("stat")
        val stat: Stat? = null
    ) {
        @Serializable
        data class Stat(
            @SerialName("name")
            val name: String? = null,
        )
    }

    @Serializable
    data class Type(
        @SerialName("type")
        val type: Type? = null
    ) {
        @Serializable
        data class Type(
            @SerialName("name")
            val name: String? = null,
        )
    }
}