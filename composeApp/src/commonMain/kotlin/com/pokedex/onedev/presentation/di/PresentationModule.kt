package com.pokedex.onedev.presentation.di

import com.pokedex.onedev.presentation.viewmodel.PokemonDetailViewModel
import com.pokedex.onedev.presentation.viewmodel.PokemonViewModel
import org.koin.dsl.module

val presentationModule = module {
    single { PokemonViewModel(get()) }
    single { PokemonDetailViewModel(get()) }
}