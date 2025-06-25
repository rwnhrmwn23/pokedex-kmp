package com.pokedex.onedev

import android.app.Application
import com.pokedex.onedev.presentation.core.di.initKoin
import org.koin.android.ext.koin.androidContext

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MainApplication)
        }
    }
}