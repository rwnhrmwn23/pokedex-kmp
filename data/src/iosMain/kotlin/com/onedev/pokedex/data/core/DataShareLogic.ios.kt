package com.onedev.pokedex.data.core

import platform.Foundation.NSBundle

actual fun getBaseUrl(): String {
    val baseUrl = NSBundle.mainBundle.objectForInfoDictionaryKey("baseUrl") as? String
    return baseUrl ?: throw IllegalStateException("Base URL not found in Info.plist")
}

actual fun getProfileImageUrl(): String {
    val baseUrl = NSBundle.mainBundle.objectForInfoDictionaryKey("profileImageUrl") as? String
    return baseUrl ?: throw IllegalStateException("Profile Image URL not found in Info.plist")
}