package com.onedev.pokedex.domain.di

import com.onedev.pokedex.domain.usecase.GetPokemonDetailUseCase
import com.onedev.pokedex.domain.usecase.GetPokemonListUseCase
import org.koin.dsl.module

val domainModule = module {
    single { GetPokemonListUseCase(get()) }
    single { GetPokemonDetailUseCase(get()) }
}