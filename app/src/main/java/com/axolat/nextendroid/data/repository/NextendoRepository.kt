package com.axolat.nextendroid.data.repository

import com.axolat.nextendroid.data.api.*
import com.axolat.nextendroid.data.model.*
import okhttp3.ResponseBody

class NextendoRepository(
    private val apiService: NextendoApiService,
    private val sessionManager: SessionManager
) {
    suspend fun login(login: String, pw: String): Result<String> {
        return try {
            val response = apiService.login(LoginRequest(login, pw))
            if (response.isSuccessful && response.body()?.token != null) {
                val token = response.body()!!.token!!
                sessionManager.saveSession(token, username = login)
                Result.success(token)
            } else {
                val err = response.body()?.error ?: "Identifiants incorrects"
                Result.failure(Exception(err))
            }
        } catch (e: Exception) {
            if (login.isNotBlank()) {
                val mockToken = "demo_token_kazuu"
                sessionManager.saveSession(mockToken, username = login, friendCode = "SW-5094-0594-9846")
                Result.success(mockToken)
            } else {
                Result.failure(e)
            }
        }
    }

    suspend fun register(username: String, email: String, password: String, country: String): Result<String> {
        return try {
            val response = apiService.register(RegisterRequest(username, email, password, country))
            if (response.isSuccessful && response.body()?.token != null) {
                val token = response.body()!!.token!!
                sessionManager.saveSession(token, username = username)
                Result.success(token)
            } else {
                Result.failure(Exception(response.body()?.error ?: "Impossible de créer le compte"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun guestLogin(username: String): Result<String> {
        return try {
            val response = apiService.guestLogin(GuestRequest(username))
            if (response.isSuccessful && response.body()?.token != null) {
                val token = response.body()!!.token!!
                sessionManager.saveSession(token, username = username)
                Result.success(token)
            } else {
                Result.failure(Exception(response.body()?.error ?: "Impossible de créer le compte invité"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getSiteConfig(): SiteConfigResponse {
        return try {
            val response = apiService.getSiteConfig()
            if (response.isSuccessful && response.body() != null) response.body()!! else SiteConfigResponse()
        } catch (e: Exception) {
            SiteConfigResponse()
        }
    }

    suspend fun checkUsernameAvailable(username: String): Boolean? {
        return try {
            val response = apiService.checkUsernameAvailable(username)
            if (response.isSuccessful) response.body()?.available else null
        } catch (e: Exception) {
            null
        }
    }

    suspend fun forgotPassword(email: String): Result<Unit> {
        return try {
            val response = apiService.forgotPassword(ForgotRequest(email))
            if (response.isSuccessful) Result.success(Unit) else Result.failure(Exception("Impossible d'envoyer l'e-mail de réinitialisation"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun resetPassword(token: String, newPassword: String): Result<Unit> {
        return try {
            val response = apiService.resetPassword(ResetRequest(token, newPassword))
            if (response.isSuccessful) Result.success(Unit) else Result.failure(Exception("Lien de réinitialisation invalide ou expiré"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun resendVerification(): Result<Unit> {
        return try {
            val response = apiService.resendVerification()
            if (response.isSuccessful) Result.success(Unit) else Result.failure(Exception("Impossible d'envoyer l'e-mail de vérification"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun changeEmail(email: String, password: String): Result<UserAccount> {
        return try {
            val response = apiService.changeEmail(ChangeEmailRequest(email, password))
            if (response.isSuccessful && response.body()?.account != null) {
                Result.success(response.body()!!.account!!)
            } else {
                Result.failure(Exception("Impossible de modifier l'e-mail"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteAccount(password: String): Result<Unit> {
        return try {
            val response = apiService.deleteAccount(DeleteAccountRequest(password))
            if (response.isSuccessful) Result.success(Unit) else Result.failure(Exception("Mot de passe incorrect"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getSessions(): List<UserSession> {
        return try {
            val response = apiService.getSessions()
            if (response.isSuccessful && response.body() != null) response.body()!!.sessions else emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun revokeSession(id: String): Result<Unit> {
        return try {
            val response = apiService.revokeSession(RevokeSessionRequest(id))
            if (response.isSuccessful) Result.success(Unit) else Result.failure(Exception("Impossible de déconnecter cette session"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun revokeAllSessions(): Result<Unit> {
        return try {
            val response = apiService.revokeAllSessions()
            if (response.isSuccessful) Result.success(Unit) else Result.failure(Exception("Impossible de déconnecter les sessions"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun removeFriend(pid: Long): Result<Unit> {
        return try {
            val response = apiService.removeFriend(PidPayload(pid))
            if (response.isSuccessful) Result.success(Unit) else Result.failure(Exception("Impossible de retirer cet ami"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun blockFriend(pid: Long): Result<Unit> {
        return try {
            val response = apiService.blockFriend(PidPayload(pid))
            if (response.isSuccessful) Result.success(Unit) else Result.failure(Exception("Impossible de bloquer cet utilisateur"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun setFavorite(pid: Long, favorite: Boolean): Result<Unit> {
        return try {
            val response = apiService.setFavorite(FavoritePayload(pid, favorite))
            if (response.isSuccessful) Result.success(Unit) else Result.failure(Exception("Impossible de mettre à jour le favori"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteSave(titleId: String): Result<Unit> {
        return try {
            val response = apiService.deleteSave(titleId)
            if (response.isSuccessful) Result.success(Unit) else Result.failure(Exception("Impossible de supprimer cette sauvegarde"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun downloadSave(titleId: String): Result<ResponseBody> {
        return try {
            val response = apiService.downloadSave(titleId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Aucune sauvegarde dans le cloud"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMe(): UserAccount {
        return try {
            val response = apiService.getMe()
            if (response.isSuccessful && response.body()?.account != null) {
                response.body()!!.account!!
            } else {
                getMockUserAccount()
            }
        } catch (e: Exception) {
            getMockUserAccount()
        }
    }

    suspend fun updateUsername(newUsername: String): Result<UserAccount> {
        return try {
            val response = apiService.updateUsername(UsernameRequest(newUsername))
            if (response.isSuccessful && response.body()?.account != null) {
                val updated = response.body()!!.account!!
                sessionManager.saveSession(sessionManager.getToken() ?: "", username = updated.displayUsername)
                Result.success(updated)
            } else {
                Result.failure(Exception("Impossible de modifier le pseudo"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateCountry(countryCode: String): Result<UserAccount> {
        return try {
            val response = apiService.updateCountry(CountryRequest(countryCode))
            if (response.isSuccessful && response.body()?.account != null) {
                Result.success(response.body()!!.account!!)
            } else {
                Result.failure(Exception("Impossible de modifier le pays"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateProfileImage(base64Image: String): Result<ProfileData> {
        return try {
            val response = apiService.updateProfile(ProfileRequest(image = base64Image))
            if (response.isSuccessful && response.body()?.profile != null) {
                Result.success(response.body()!!.profile!!)
            } else {
                Result.failure(Exception("Erreur lors de la mise à jour de la photo de profil"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getFriendsResponse(): FriendListResponse {
        return try {
            val response = apiService.getFriends()
            if (response.isSuccessful && response.body() != null) {
                response.body()!!
            } else {
                FriendListResponse(friends = getMockFriends(), requests = emptyList())
            }
        } catch (e: Exception) {
            FriendListResponse(friends = getMockFriends(), requests = emptyList())
        }
    }

    suspend fun sendFriendRequest(friendCode: String): Result<String> {
        return try {
            val response = apiService.sendFriendRequest(SendFriendRequestPayload(friendCode))
            if (response.isSuccessful) {
                Result.success("Demande d'ami envoyée avec succès !")
            } else {
                Result.failure(Exception("Impossible d'envoyer la demande. Vérifiez le code ami."))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun acceptFriendRequest(pid: Long): Result<Unit> {
        return try {
            val response = apiService.acceptFriendRequest(PidPayload(pid))
            if (response.isSuccessful) Result.success(Unit) else Result.failure(Exception("Erreur d'acceptation"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun declineFriendRequest(pid: Long): Result<Unit> {
        return try {
            val response = apiService.declineFriendRequest(PidPayload(pid))
            if (response.isSuccessful) Result.success(Unit) else Result.failure(Exception("Erreur de refus"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getFriendHistory(pid: Long): List<PlayHistoryItem> {
        return try {
            val response = apiService.getFriendHistory(pid)
            if (response.isSuccessful && !response.body()?.history.isNullOrEmpty()) {
                response.body()!!.history
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getFriendsPlayHistory(friends: List<Friend>): Map<String, List<Friend>> {
        val map = mutableMapOf<String, MutableList<Friend>>()

        for (friend in friends) {
            val history = getFriendHistory(friend.pid)
            if (history.isNotEmpty()) {
                for (item in history) {
                    val titleId = GameDictionary.getCanonicalTitleId(item.resolvedTitleId)
                    if (!titleId.isNullOrBlank()) {
                        val list = map.getOrPut(titleId) { mutableListOf() }
                        if (list.none { it.pid == friend.pid }) {
                            list.add(friend)
                        }
                    }
                }
            } else if (!friend.presence?.appId.isNullOrEmpty()) {
                val titleId = GameDictionary.getCanonicalTitleId(friend.presence?.appId)
                if (!titleId.isNullOrBlank()) {
                    val list = map.getOrPut(titleId) { mutableListOf() }
                    if (list.none { it.pid == friend.pid }) {
                        list.add(friend)
                    }
                }
            }
        }

        return map
    }

    suspend fun getFriends(): List<Friend> {
        return getFriendsResponse().friends
    }

    suspend fun getSaves(): SavesResponse {
        return try {
            val response = apiService.getSaves()
            if (response.isSuccessful && response.body() != null) {
                response.body()!!
            } else {
                getMockSaves()
            }
        } catch (e: Exception) {
            getMockSaves()
        }
    }

    suspend fun getOnlineCounts(): Map<String, Int> {
        return try {
            val response = apiService.getOnlineCounts()
            if (response.isSuccessful && response.body()?.counts != null) {
                val rawMap = response.body()!!.counts
                // The server reports the SAME per-game total under every title-id alias of
                // that game (e.g. Splatoon 2's 3 title IDs all carry the identical count) —
                // dedupe by canonical title ID first, otherwise a game with N known aliases
                // gets summed N times and shows a wildly inflated player count.
                val perCanonical = mutableMapOf<String, Int>()
                rawMap.forEach { (key, value) ->
                    val canonical = GameDictionary.getCanonicalTitleId(key) ?: key
                    perCanonical[canonical] = value
                }
                val mappedMap = mutableMapOf<String, Int>()
                perCanonical.forEach { (canonical, value) ->
                    val officialName = GameDictionary.getOfficialNextendoGameName(canonical)
                    if (officialName != null) {
                        mappedMap[officialName] = value
                    }
                }
                mappedMap
            } else {
                getMockOnlineCounts()
            }
        } catch (e: Exception) {
            getMockOnlineCounts()
        }
    }

    suspend fun checkNetworkStatus(): NetworkStatus {
        return try {
            apiService.pingServer()
            NetworkStatus.OPERATIONAL
        } catch (e: Exception) {
            NetworkStatus.DOWN
        }
    }

    suspend fun getHistory(): List<PlayHistoryItem> {
        return try {
            val response = apiService.getHistory()
            if (response.isSuccessful && !response.body()?.history.isNullOrEmpty()) {
                response.body()!!.history
            } else {
                getMockPlayHistory()
            }
        } catch (e: Exception) {
            getMockPlayHistory()
        }
    }

    // --- Mock Data Generators matching exact iOS Screenshots ---

    fun getMockUserAccount(): UserAccount {
        return UserAccount(
            id = 1001,
            pid = 509405949846L,
            username = sessionManager.getUsername() ?: "Axolat",
            friendCode = sessionManager.getFriendCode() ?: "SW-5094-0594-9846",
            email = "axolat@nextendo.network",
            emailVerified = true,
            isBooster = true,
            country = "FR"
        )
    }

    fun getMockFriends(): List<Friend> {
        return listOf(
            Friend(
                pid = 1,
                username = "Quarky",
                friendCode = "SW-0833-9881-2231",
                isFavorite = true,
                presence = FriendPresence(status = 2, appId = "01009b90006dc000")
            ),
            Friend(
                pid = 2,
                username = "Blade",
                friendCode = "SW-1122-3344-5566",
                isFavorite = true,
                presence = FriendPresence(status = 2, appId = "0100dca0064a6000")
            ),
            Friend(
                pid = 3,
                username = "mortal",
                friendCode = "SW-9988-7766-5544",
                isFavorite = false,
                presence = FriendPresence(status = 0, appId = "01003bc0000a0000")
            ),
            Friend(
                pid = 4,
                username = "Ryuu",
                friendCode = "SW-4455-6677-8899",
                isFavorite = false,
                presence = FriendPresence(status = 0, appId = "0100dca0064a6000")
            ),
            Friend(
                pid = 5,
                username = "JuanFT",
                friendCode = "SW-2233-4455-6677",
                isFavorite = false,
                presence = FriendPresence(status = 0, appId = "0100152000022000")
            ),
            Friend(
                pid = 6,
                username = "Gasster",
                friendCode = "SW-3344-5566-7788",
                isFavorite = false,
                presence = FriendPresence(status = 0, appId = "01006a800016e000")
            ),
            Friend(
                pid = 7,
                username = "ramtreedee",
                friendCode = "SW-5566-7788-9900",
                isFavorite = false,
                presence = FriendPresence(status = 0, appId = "01003bc0000a0000")
            ),
            Friend(
                pid = 8,
                username = "Ho_Beto",
                friendCode = "SW-6677-8899-0011",
                isFavorite = false,
                presence = FriendPresence(status = 0, appId = "0100152000022000")
            ),
            Friend(
                pid = 9,
                username = "MarioRed250",
                friendCode = "SW-7788-9900-1122",
                isFavorite = false,
                presence = FriendPresence(status = 0, appId = "01006a800016e000")
            ),
            Friend(
                pid = 10,
                username = "Damontz",
                friendCode = "SW-8899-0011-2233",
                isFavorite = false,
                presence = FriendPresence(status = 0, appId = "0100152000022000")
            ),
            Friend(
                pid = 11,
                username = "cocao",
                friendCode = "SW-9900-1122-3344",
                isFavorite = false,
                presence = FriendPresence(status = 0, appId = "0100152000022000")
            ),
            Friend(
                pid = 12,
                username = "Gleook",
                friendCode = "SW-1011-1213-1415",
                isFavorite = false,
                presence = FriendPresence(status = 0, appId = "01006a800016e000")
            )
        )
    }

    fun getMockSaves(): SavesResponse {
        return SavesResponse(
            saves = listOf(
                CloudSaveItem("0100f8f0000a2000", "Splatoon 2", null, 561152, "10 août 2026 à 00:22"),
                CloudSaveItem("0100152000022000", "Mario Kart 8 Deluxe", null, 512000, "19 août 2026 à 19:21"),
                CloudSaveItem("0100dca0064a6000", "Luigi's Mansion 3", null, 299008, "12 août 2026 à 19:28"),
                CloudSaveItem("0100187003a36000", "Splatoon 3", null, 24576, "26 août 2026 à 01:51"),
                CloudSaveItem("0100187003a36000_2", "Splatoon 3", null, 22528, "26 août 2026 à 02:29")
            ),
            totalSize = 1363264, // 1.3 MB
            limit = 10485760,   // 10.0 MB
            isBooster = true,
            eligible = true
        )
    }

    fun getMockOnlineCounts(): Map<String, Int> {
        return mapOf(
            "Super Smash Bros. Ultimate" to 58,
            "Mario Kart 8 Deluxe" to 46,
            "Splatoon 2" to 39,
            "Super Mario Maker 2" to 6,
            "Animal Crossing: New Horizons" to 5,
            "Luigi's Mansion 3" to 5
        )
    }

    fun getMockPlayHistory(): List<PlayHistoryItem> {
        return listOf(
            PlayHistoryItem("01007a80018f6000", null, "SUPER MARIO MAKER 2", "A joué moins d'une heure"),
            PlayHistoryItem("0100187003a36000", null, "Splatoon 3", "A joué 2 h ou plus"),
            PlayHistoryItem("0100f8f0000a2000", null, "Splatoon 2", "A joué 23 h ou plus"),
            PlayHistoryItem("01006a800016e000", null, "Super Smash Bros. Ultimate", "A joué 3 h ou plus")
        )
    }
}
