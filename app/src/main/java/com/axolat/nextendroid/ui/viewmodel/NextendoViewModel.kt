package com.axolat.nextendroid.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.axolat.nextendroid.data.api.ApiClient
import com.axolat.nextendroid.data.model.*
import com.axolat.nextendroid.data.repository.NextendoRepository
import com.axolat.nextendroid.data.repository.SessionManager
import com.axolat.nextendroid.ui.theme.AppLanguage
import com.axolat.nextendroid.ui.theme.NextendoPink
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NextendoViewModel(application: Application) : AndroidViewModel(application) {

    val sessionManager = SessionManager(application)
    private val apiService = ApiClient.createService(sessionManager)
    private val repository = NextendoRepository(apiService, sessionManager)

    private val _isLoggedIn = MutableStateFlow(sessionManager.isLoggedIn())
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _appLanguage = MutableStateFlow(
        if (sessionManager.getLanguage() == "en") AppLanguage.EN else AppLanguage.FR
    )
    val appLanguage: StateFlow<AppLanguage> = _appLanguage.asStateFlow()

    private val _accentColorHex = MutableStateFlow(sessionManager.getAccentColorHex())
    val accentColorHex: StateFlow<String> = _accentColorHex.asStateFlow()

    private val _currentUser = MutableStateFlow<UserAccount?>(null)
    val currentUser: StateFlow<UserAccount?> = _currentUser.asStateFlow()

    private val _friends = MutableStateFlow<List<Friend>>(emptyList())
    val friends: StateFlow<List<Friend>> = _friends.asStateFlow()

    private val _friendsPlayedGames = MutableStateFlow<Map<String, List<Friend>>>(emptyMap())
    val friendsPlayedGames: StateFlow<Map<String, List<Friend>>> = _friendsPlayedGames.asStateFlow()

    private val _friendRequests = MutableStateFlow<List<FriendRequest>>(emptyList())
    val friendRequests: StateFlow<List<FriendRequest>> = _friendRequests.asStateFlow()

    private val _selectedFriend = MutableStateFlow<Friend?>(null)
    val selectedFriend: StateFlow<Friend?> = _selectedFriend.asStateFlow()

    private val _savesResponse = MutableStateFlow<SavesResponse?>(null)
    val savesResponse: StateFlow<SavesResponse?> = _savesResponse.asStateFlow()

    private val _onlineCounts = MutableStateFlow<Map<String, Int>>(emptyMap())
    val onlineCounts: StateFlow<Map<String, Int>> = _onlineCounts.asStateFlow()

    private val _playHistory = MutableStateFlow<List<PlayHistoryItem>>(emptyList())
    val playHistory: StateFlow<List<PlayHistoryItem>> = _playHistory.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> = _loginError.asStateFlow()

    init {
        val storedHex = sessionManager.getAccentColorHex()
        updateNextendoPinkColor(storedHex)
        loadAllData()
        startAutoRefreshLoop()
    }

    private fun startAutoRefreshLoop() {
        viewModelScope.launch {
            while (true) {
                delay(15_000) // Auto-refresh every 15 seconds
                if (_isLoggedIn.value) {
                    refreshLiveDataSilently()
                }
            }
        }
    }

    fun refreshLiveData() {
        viewModelScope.launch {
            _isLoading.value = true
            refreshLiveDataSilently()
            _isLoading.value = false
        }
    }

    private suspend fun refreshLiveDataSilently() {
        try {
            val friendsResp = repository.getFriendsResponse()
            _friends.value = friendsResp.friends
            _friendRequests.value = friendsResp.requests

            val playedGamesMap = repository.getFriendsPlayHistory(friendsResp.friends)
            _friendsPlayedGames.value = playedGamesMap

            val counts = repository.getOnlineCounts()
            _onlineCounts.value = counts

            _savesResponse.value = repository.getSaves()
        } catch (e: Exception) {
            // ignore
        }
    }

    fun setLanguage(lang: AppLanguage) {
        _appLanguage.value = lang
        sessionManager.saveLanguage(lang.code)
    }

    fun setAccentColorHex(hex: String) {
        _accentColorHex.value = hex
        sessionManager.saveAccentColorHex(hex)
        updateNextendoPinkColor(hex)
    }

    private fun updateNextendoPinkColor(hex: String) {
        try {
            val parsed = android.graphics.Color.parseColor(hex)
            NextendoPink = androidx.compose.ui.graphics.Color(parsed)
        } catch (e: Exception) {
            // ignore
        }
    }

    fun updateUsername(newUsername: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val currentImg = _currentUser.value?.image
            val currentAv = _currentUser.value?.avatar
            val res = repository.updateUsername(newUsername)
            _isLoading.value = false
            if (res.isSuccess) {
                val updated = res.getOrNull()
                if (updated != null) {
                    _currentUser.value = updated.copy(
                        image = updated.image ?: currentImg,
                        avatar = updated.avatar ?: currentAv
                    )
                }
            }
        }
    }

    fun updateCountry(countryCode: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val currentImg = _currentUser.value?.image
            val currentAv = _currentUser.value?.avatar
            val res = repository.updateCountry(countryCode)
            _isLoading.value = false
            if (res.isSuccess) {
                val updated = res.getOrNull()
                if (updated != null) {
                    _currentUser.value = updated.copy(
                        image = updated.image ?: currentImg,
                        avatar = updated.avatar ?: currentAv
                    )
                }
            }
        }
    }

    fun updateProfileImage(base64Image: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val current = _currentUser.value
            val res = repository.updateProfileImage(base64Image)
            _isLoading.value = false
            if (res.isSuccess) {
                val updatedProf = res.getOrNull()
                _currentUser.value = current?.copy(
                    image = updatedProf?.image ?: base64Image,
                    avatar = updatedProf?.avatar
                )
            } else {
                // Optimistic local update fallback
                _currentUser.value = current?.copy(
                    image = base64Image
                )
            }
        }
    }

    fun sendFriendRequest(friendCode: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            val res = repository.sendFriendRequest(friendCode)
            if (res.isSuccess) {
                onResult(true, res.getOrDefault("Demande envoyée !"))
                loadAllData()
            } else {
                onResult(false, res.exceptionOrNull()?.message ?: "Impossible d'envoyer la demande")
            }
        }
    }

    fun acceptFriendRequest(pid: Long) {
        viewModelScope.launch {
            val res = repository.acceptFriendRequest(pid)
            if (res.isSuccess) {
                loadAllData()
            }
        }
    }

    fun declineFriendRequest(pid: Long) {
        viewModelScope.launch {
            val res = repository.declineFriendRequest(pid)
            if (res.isSuccess) {
                loadAllData()
            }
        }
    }

    fun login(email: String, pw: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _loginError.value = null
            val result = repository.login(email, pw)
            _isLoading.value = false

            if (result.isSuccess) {
                _isLoggedIn.value = true
                loadAllData()
            } else {
                _loginError.value = result.exceptionOrNull()?.message ?: "Erreur de connexion"
            }
        }
    }

    fun logout() {
        sessionManager.clearSession()
        _isLoggedIn.value = false
        _currentUser.value = null
    }

    fun selectFriend(friend: Friend) {
        _selectedFriend.value = friend
    }

    fun loadAllData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                var acct = repository.getMe()
                if (acct.image.isNullOrBlank() && acct.avatar.isNullOrBlank()) {
                    try {
                        val profileResp = apiService.getProfile()
                        if (profileResp.isSuccessful && profileResp.body()?.profile != null) {
                            val p = profileResp.body()?.profile
                            if (p != null) {
                                acct = acct.copy(image = p.image, avatar = p.avatar)
                            }
                        }
                    } catch (e: Exception) {
                        // ignore
                    }
                }
                _currentUser.value = acct

                val friendsResp = repository.getFriendsResponse()
                _friends.value = friendsResp.friends
                _friendRequests.value = friendsResp.requests

                val playedGamesMap = repository.getFriendsPlayHistory(friendsResp.friends)
                _friendsPlayedGames.value = playedGamesMap

                _savesResponse.value = repository.getSaves()

                val counts = repository.getOnlineCounts()
                _onlineCounts.value = counts

                _playHistory.value = repository.getHistory()
            } catch (e: Exception) {
                // Ignore fallback handles it
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleFavoriteFriend(pid: Long) {
        viewModelScope.launch {
            _friends.value = _friends.value.map { friend ->
                if (friend.pid == pid) {
                    friend.copy(isFavorite = !friend.isFavorite)
                } else friend
            }
            try {
                apiService.toggleFavorite(pid)
            } catch (e: Exception) {
                // ignore
            }
        }
    }
}
