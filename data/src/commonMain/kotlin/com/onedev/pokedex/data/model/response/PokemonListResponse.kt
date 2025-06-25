package com.onedev.pokedex.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonListResponse(
    @SerialName("count")
    val count: Int? = null,
    @SerialName("next")
    val next: String? = null,
    @SerialName("previous")
    val previous: String? = null,
    @SerialName("results")
    val results: List<Result?>? = null
) {
    @Serializable
    data class Result(
        @SerialName("name")
        val name: String? = null,
        @SerialName("url")
        val url: String? = null
    )
}