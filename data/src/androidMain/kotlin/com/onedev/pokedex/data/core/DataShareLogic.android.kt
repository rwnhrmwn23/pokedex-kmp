package com.onedev.pokedex.data.core

actual fun getBaseUrl(): String {
    return NativeLib.getBaseUrl()
}

actual fun getProfileImageUrl(): String {
    return NativeLib.getProfileImageUrl()
}