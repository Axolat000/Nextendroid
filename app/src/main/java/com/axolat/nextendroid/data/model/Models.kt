package com.axolat.nextendroid.data.model

import com.google.gson.annotations.SerializedName

// --- Country Utils ---
object CountryUtils {
    fun getCountryFlag(countryCode: String?): String {
        if (countryCode.isNullOrBlank()) return "🇫🇷"
        val code = countryCode.trim().uppercase()
        return when (code) {
            "FR" -> "🇫🇷"
            "US" -> "🇺🇸"
            "GB" -> "🇬🇧"
            "DE" -> "🇩🇪"
            "ES" -> "🇪🇸"
            "IT" -> "🇮🇹"
            "JP" -> "🇯🇵"
            "CA" -> "🇨🇦"
            "BR" -> "🇧🇷"
            "MX" -> "🇲🇽"
            "PT" -> "🇵🇹"
            "RU" -> "🇷🇺"
            else -> "🏴"
        }
    }

    fun getCountryName(countryCode: String?): String {
        if (countryCode.isNullOrBlank()) return "France"
        val code = countryCode.trim().uppercase()
        return when (code) {
            "FR" -> "France"
            "US" -> "États-Unis"
            "GB" -> "Royaume-Uni"
            "DE" -> "Allemagne"
            "ES" -> "Espagne"
            "IT" -> "Italie"
            "JP" -> "Japon"
            "CA" -> "Canada"
            "BR" -> "Brésil"
            "MX" -> "Mexique"
            "PT" -> "Portugal"
            "RU" -> "Russie"
            else -> code
        }
    }

    fun getAllCountries(): List<Pair<String, String>> {
        return listOf(
            "FR" to "France",
            "US" to "États-Unis",
            "GB" to "Royaume-Uni",
            "DE" to "Allemagne",
            "ES" to "Espagne",
            "IT" to "Italie",
            "JP" to "Japon",
            "CA" to "Canada",
            "BR" to "Brésil",
            "MX" to "Mexique",
            "PT" to "Portugal",
            "RU" to "Russie"
        )
    }
}

// --- Auth ---
data class LoginRequest(
    val login: String,
    val password: String
)

data class LoginResponse(
    val token: String?,
    val error: String?
)

// --- User Account ---
data class MeResponse(
    val account: UserAccount?
)

data class UserAccount(
    val id: Long = 0,
    val pid: Long = 0,
    val username: String = "",
    val name: String? = null,
    @SerializedName("friend_code") val friendCode: String = "",
    val email: String = "",
    @SerializedName("email_verified") val emailVerified: Boolean = false,
    @SerializedName("isBooster") val isBooster: Boolean = false,
    @SerializedName("is_booster") val legacyIsBooster: Boolean = false,
    @SerializedName("discord") val discordUsername: String? = null,
    @SerializedName("discord_id") val discordId: String? = null,
    val country: String? = null,
    val image: String? = null,
    val avatar: String? = null
) {
    val displayUsername: String
        get() = name.takeIf { !it.isNullOrBlank() } ?: username

    val formattedAvatarUrl: String?
        get() = GameDictionary.getAvatarUrl(image, avatar)

    val isDiscordLinked: Boolean
        get() = !discordUsername.isNullOrBlank() || !discordId.isNullOrBlank()

    val effectiveIsBooster: Boolean
        get() = isBooster || legacyIsBooster
}

// --- Profile ---
data class ProfileResponse(
    val profile: ProfileData?
)

data class ProfileData(
    val image: String? = null,
    val avatar: String? = null,
    val username: String? = null,
    val name: String? = null
)

// --- Friend & Presence ---
data class FriendListResponse(
    val friends: List<Friend> = emptyList(),
    val requests: List<FriendRequest> = emptyList()
)

data class FriendRequest(
    val pid: Long = 0,
    val username: String = "",
    val name: String? = null,
    @SerializedName("friend_code") val friendCode: String = "",
    val image: String? = null,
    val avatar: String? = null
)

data class Friend(
    val pid: Long = 0,
    val username: String = "",
    val name: String? = null,
    @SerializedName("friend_code") val friendCode: String = "",
    @SerializedName("is_favorite") val isFavorite: Boolean = false,
    @SerializedName("favorite") val favorite: Boolean = false,
    val presence: FriendPresence? = null,
    val image: String? = null,
    val avatar: String? = null,
    @SerializedName("last_seen") val lastSeen: String? = null
) {
    val displayUsername: String
        get() = name.takeIf { !it.isNullOrBlank() } ?: username

    val formattedAvatarUrl: String?
        get() = GameDictionary.getAvatarUrl(image, avatar)
}

data class FriendPresence(
    val status: Int = 0, // 0 = Offline, 1 = Online, 2 = OnlinePlay
    @SerializedName("app_id") val appId: String? = null,
    @SerializedName("app_detail") val appDetail: String? = null,
    @SerializedName("app_field") val appField: String? = null
) {
    val resolvedGameName: String
        get() = GameDictionary.getGameName(appId)
}

// --- History ---
data class PlayHistoryResponse(
    val history: List<PlayHistoryItem> = emptyList()
)

data class PlayHistoryItem(
    @SerializedName("titleId") val titleId: String? = null,
    @SerializedName("title_id") val legacyTitleId: String? = null,
    @SerializedName("title_name") val titleName: String? = null,
    @SerializedName("played_time") val playedTime: String? = null,
    @SerializedName("last_played") val lastPlayed: String? = null
) {
    val resolvedTitleId: String
        get() = titleId ?: legacyTitleId ?: ""

    val resolvedName: String
        get() = titleName.takeIf { !it.isNullOrBlank() } ?: GameDictionary.getGameName(resolvedTitleId)
}

// --- Cloud Saves ---
data class SavesResponse(
    val saves: List<CloudSaveItem> = emptyList(),
    val totalSize: Long = 0,
    val limit: Long = 5242880,
    val isBooster: Boolean = false,
    val eligible: Boolean = true,
    val reason: String? = null
)

data class CloudSaveItem(
    @SerializedName("titleId") val titleId: String = "",
    val name: String? = null,
    val icon: String? = null,
    val size: Long = 0,
    @SerializedName("updatedAt") val updatedAt: String? = null
) {
    val resolvedName: String
        get() = name.takeIf { !it.isNullOrBlank() } ?: GameDictionary.getGameName(titleId)
}

// --- Online Counts ---
data class OnlineCountsResponse(
    val counts: Map<String, Int> = emptyMap()
)

// --- Game Info ---
data class GameInfoResponse(
    @SerializedName("title_id") val titleId: String? = null,
    val name: String? = null,
    val tagline: String? = null,
    val description: String? = null,
    val hero: String? = null,
    val cover: String? = null
)
