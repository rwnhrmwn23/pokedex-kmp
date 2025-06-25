package com.onedev.pokedex.data.di

import PokemonRepositoryImpl
import com.onedev.pokedex.data.api.PokemonApi
import com.onedev.pokedex.domain.repository.PokemonRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val dataModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    }
                )
            }
        }
    }
    single { PokemonApi(get()) }
    single<PokemonRepository> { PokemonRepositoryImpl(get()) }
}