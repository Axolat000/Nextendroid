package com.axolat.nextendroid.data.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

object GameDictionary {
    val canonicalMap = mapOf(
        "0100ad9012510000" to Pair("PAC-MAN 99", "0100ad9012510000"),
        "0100277011f1a000" to Pair("SUPER MARIO BROS. 35", "0100277011f1a000"),
        "0100dca0064a6000" to Pair("Luigi's Mansion 3", "0100dca0064a6000"),
        "01009b500007c000" to Pair("ARMS", "01009b500007c000"),
        "0100bde00862a000" to Pair("Mario Tennis Aces", "0100bde00862a000"),
        "0100152000022000" to Pair("Mario Kart 8 Deluxe", "0100152000022000"),
        "01003bc0000a0000" to Pair("Splatoon 2", "01003bc0000a0000"),
        "0100f8f0000a2000" to Pair("Splatoon 2", "01003bc0000a0000"),
        "01003c700009c800" to Pair("Splatoon 2", "01003bc0000a0000"),
        "01006a800016e000" to Pair("Super Smash Bros. Ultimate", "01006a800016e000"),
        "0100c2500fc20000" to Pair("Splatoon 3", "0100c2500fc20000"),
        "0100187003a36000" to Pair("Splatoon 3", "0100c2500fc20000"),
        "01006f8002326000" to Pair("Animal Crossing: New Horizons", "01006f8002326000"),
        "01009b90006dc000" to Pair("Super Mario Maker 2", "01009b90006dc000"),
        "01007a80018f6000" to Pair("Super Mario Maker 2", "01009b90006dc000"),
        "010019401051c000" to Pair("Mario Strikers: Battle League", "010019401051c000"),
        "0100d71004694000" to Pair("Minecraft", "0100d71004694000"),
        "01006bd001e06000" to Pair("Minecraft", "0100d71004694000"),
        "0100965017338000" to Pair("Mario Party Jamboree", "0100965017338000")
    )

    fun isOfficialNextendoGame(titleIdOrName: String?): Boolean {
        if (titleIdOrName.isNullOrBlank()) return false
        val canonical = getCanonicalTitleId(titleIdOrName) ?: return false
        return canonicalMap.containsKey(canonical)
    }

    fun getOfficialNextendoGameName(titleId: String?): String? {
        if (titleId.isNullOrBlank()) return null
        val clean = titleId.trim()
        val lower = clean.lowercase()
        if (canonicalMap.values.any { it.first.equals(clean, ignoreCase = true) }) {
            return clean
        }
        return canonicalMap[lower]?.first
    }

    fun getGameName(titleIdOrName: String?): String {
        if (titleIdOrName.isNullOrBlank()) return "En ligne"
        val clean = titleIdOrName.trim()
        val lower = clean.lowercase()
        if (canonicalMap.values.any { it.first.equals(clean, ignoreCase = true) }) {
            return clean
        }
        if (!lower.startsWith("0100") || lower.length != 16) {
            return clean
        }
        return canonicalMap[lower]?.first ?: clean
    }

    fun getCanonicalTitleId(titleIdOrName: String?): String? {
        if (titleIdOrName.isNullOrBlank()) return null
        val clean = titleIdOrName.trim()
        val lower = clean.lowercase()

        // 1. Direct hex lookup
        canonicalMap[lower]?.let { return it.second }

        // 2. Lookup by game name
        val matchByName = canonicalMap.values.find { it.first.equals(clean, ignoreCase = true) }
        if (matchByName != null) {
            return matchByName.second
        }

        return if (lower.startsWith("0100") && lower.length == 16) lower else null
    }

    fun getGameCoverUrl(titleIdOrName: String?): String? {
        val canonical = getCanonicalTitleId(titleIdOrName) ?: return null
        if (canonicalMap.containsKey(canonical)) {
            return "file:///android_asset/covers/$canonical.jpg"
        }
        // Title not bundled locally (server added a game since this app build) — fall back
        // to the live server-hosted cover so new games still render art without an app update.
        return "https://nextendo.network/api/gamemedia/$canonical/cover"
    }

    fun getAvatarUrl(image: String?, avatar: String?): String? {
        if (!image.isNullOrBlank()) {
            return if (image.startsWith("http") || image.startsWith("data:")) {
                image
            } else {
                "data:image/jpeg;base64,$image"
            }
        }
        if (!avatar.isNullOrBlank()) {
            val clean = avatar.trim()
            return if (clean.startsWith("http")) clean else "https://nextendo.network/avatars/$clean"
        }
        return null
    }

    fun decodeBase64ToBitmap(dataStr: String?): Bitmap? {
        if (dataStr.isNullOrBlank()) return null
        return try {
            val clean = if (dataStr.contains(",")) {
                dataStr.substringAfter(",")
            } else {
                dataStr
            }
            val decodedBytes = Base64.decode(clean, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (e: Exception) {
            null
        }
    }
}
