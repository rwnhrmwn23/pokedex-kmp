package com.pokedex.onedev.presentation.utils

import com.onedev.pokedex.data.core.getProfileImageUrl

fun mapStatName(name: String): String {
    return when (name.lowercase()) {
        "hp" -> "HP"
        "attack" -> "ATK"
        "defense" -> "DEF"
        "special-attack" -> "SATK"
        "special-defense" -> "SDEF"
        "speed" -> "SPD"
        else -> name.uppercase()
    }
}

fun formatName(name: String?): String {
    return name ?: "Unknown"
}

fun formatId(id: Int?): String {
    return "#${id?.toString()?.padStart(3, '0') ?: "000"}"
}

fun formatImage(id: Int?): String {
    return "${getProfileImageUrl()}/${id}.png"
}