package com.onedev.pokedex.data.api

import com.onedev.pokedex.data.core.getBaseUrl
import com.onedev.pokedex.data.model.response.PokemonDetailResponse
import com.onedev.pokedex.data.model.response.PokemonListResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class PokemonApi(private val client: HttpClient) {
    suspend fun getPokemonList(offset: Int = 0, limit: Int = 20): PokemonListResponse {
        return client.get("${getBaseUrl()}/pokemon") {
            url {
                parameters.append("offset", offset.toString())
                parameters.append("limit", limit.toString())
            }
        }.body()
    }

    suspend fun getPokemonDetail(id: Int): PokemonDetailResponse {
        return client.get("${getBaseUrl()}/pokemon/$id").body()
    }
}