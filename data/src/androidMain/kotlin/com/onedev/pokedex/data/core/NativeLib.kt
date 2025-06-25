package com.onedev.pokedex.data.core

object NativeLib {
    init {
        System.loadLibrary("native-lib")
    }

    external fun getBaseUrl(): String

    external fun getProfileImageUrl(): String
}