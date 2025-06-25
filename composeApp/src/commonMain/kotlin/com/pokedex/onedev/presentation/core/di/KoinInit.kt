package com.pokedex.onedev.presentation.core.di

import com.onedev.pokedex.data.di.dataModule
import com.onedev.pokedex.domain.di.domainModule
import com.pokedex.onedev.presentation.di.presentationModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(
        dataModule,
        domainModule,
        presentationModule
    )
}