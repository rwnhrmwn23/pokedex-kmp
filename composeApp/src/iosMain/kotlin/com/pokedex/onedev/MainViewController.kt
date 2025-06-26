package com.pokedex.onedev

import androidx.compose.ui.window.ComposeUIViewController
import com.pokedex.onedev.presentation.core.di.initKoin

fun MainViewController() = ComposeUIViewController {
    initKoin()
    App()
}