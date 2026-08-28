package com.axolat.nextendroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.axolat.nextendroid.ui.components.BottomNavBar
import com.axolat.nextendroid.ui.components.NavTab
import com.axolat.nextendroid.ui.screens.*
import com.axolat.nextendroid.ui.theme.NextendoDarkBackground
import com.axolat.nextendroid.ui.theme.NextendroidTheme
import com.axolat.nextendroid.ui.viewmodel.NextendoViewModel

enum class SubScreen {
    NONE,
    FRIEND_DETAIL,
    ADD_FRIEND,
    SETTINGS
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NextendroidTheme {
                val viewModel: NextendoViewModel = viewModel()
                val isLoggedIn by viewModel.isLoggedIn.collectAsState()
                val currentUser by viewModel.currentUser.collectAsState()
                val friends by viewModel.friends.collectAsState()
                val friendsPlayedGames by viewModel.friendsPlayedGames.collectAsState()
                val friendRequests by viewModel.friendRequests.collectAsState()
                val selectedFriend by viewModel.selectedFriend.collectAsState()
                val savesResponse by viewModel.savesResponse.collectAsState()
                val onlineCounts by viewModel.onlineCounts.collectAsState()
                val playHistory by viewModel.playHistory.collectAsState()
                val isLoading by viewModel.isLoading.collectAsState()
                val loginError by viewModel.loginError.collectAsState()
                val appLanguage by viewModel.appLanguage.collectAsState()
                val accentColorHex by viewModel.accentColorHex.collectAsState()

                var currentTab by remember { mutableStateOf(NavTab.HOME) }
                var currentSubScreen by remember { mutableStateOf(SubScreen.NONE) }

                // Android System Back Button Handling
                BackHandler(enabled = isLoggedIn && (currentSubScreen != SubScreen.NONE || currentTab != NavTab.HOME)) {
                    if (currentSubScreen != SubScreen.NONE) {
                        currentSubScreen = SubScreen.NONE
                    } else if (currentTab != NavTab.HOME) {
                        currentTab = NavTab.HOME
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(NextendoDarkBackground)
                ) {
                    if (!isLoggedIn) {
                        LoginScreen(
                            onLoginClick = { email, pw -> viewModel.login(email, pw) },
                            isLoading = isLoading,
                            errorMessage = loginError
                        )
                    } else {
                        Scaffold(
                            bottomBar = {
                                if (currentSubScreen == SubScreen.NONE) {
                                    BottomNavBar(
                                        selectedTab = currentTab,
                                        appLanguage = appLanguage,
                                        onTabSelected = { tab ->
                                            currentTab = tab
                                            currentSubScreen = SubScreen.NONE
                                        }
                                    )
                                }
                            },
                            containerColor = NextendoDarkBackground
                        ) { innerPadding ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding)
                            ) {
                                when (currentSubScreen) {
                                    SubScreen.FRIEND_DETAIL -> {
                                        selectedFriend?.let { friend ->
                                            FriendDetailScreen(
                                                friend = friend,
                                                playHistory = playHistory,
                                                appLanguage = appLanguage,
                                                onBackClick = { currentSubScreen = SubScreen.NONE },
                                                onFavoriteToggle = { pid -> viewModel.toggleFavoriteFriend(pid) }
                                            )
                                        } ?: run {
                                            currentSubScreen = SubScreen.NONE
                                        }
                                    }
                                    SubScreen.ADD_FRIEND -> {
                                        AddFriendScreen(
                                            userFriendCode = currentUser?.friendCode ?: "SW-5094-0594-9846",
                                            incomingRequests = friendRequests,
                                            appLanguage = appLanguage,
                                            onBackClick = { currentSubScreen = SubScreen.NONE },
                                            onSendRequestClick = { code, callback ->
                                                viewModel.sendFriendRequest(code, callback)
                                            },
                                            onAcceptRequest = { pid -> viewModel.acceptFriendRequest(pid) },
                                            onDeclineRequest = { pid -> viewModel.declineFriendRequest(pid) }
                                        )
                                    }
                                    SubScreen.SETTINGS -> {
                                        SettingsScreen(
                                            currentAccentHex = accentColorHex,
                                            onAccentColorSelect = { hex -> viewModel.setAccentColorHex(hex) },
                                            onBackClick = { currentSubScreen = SubScreen.NONE }
                                        )
                                    }
                                    SubScreen.NONE -> {
                                        when (currentTab) {
                                            NavTab.HOME -> {
                                                HomeScreen(
                                                    currentUser = currentUser,
                                                    friends = friends,
                                                    friendsPlayedGames = friendsPlayedGames,
                                                    onlineCounts = onlineCounts,
                                                    appLanguage = appLanguage,
                                                    onFriendClick = { friend ->
                                                        viewModel.selectFriend(friend)
                                                        currentSubScreen = SubScreen.FRIEND_DETAIL
                                                    },
                                                    onSeeAllClick = {
                                                        currentTab = NavTab.FRIENDS
                                                    },
                                                    onRefreshClick = {
                                                        viewModel.refreshLiveData()
                                                    }
                                                )
                                            }
                                            NavTab.FRIENDS -> {
                                                FriendsScreen(
                                                    friends = friends,
                                                    appLanguage = appLanguage,
                                                    onFriendClick = { friend ->
                                                        viewModel.selectFriend(friend)
                                                        currentSubScreen = SubScreen.FRIEND_DETAIL
                                                    },
                                                    onAddFriendClick = {
                                                        currentSubScreen = SubScreen.ADD_FRIEND
                                                    },
                                                    onRefreshClick = {
                                                        viewModel.refreshLiveData()
                                                    }
                                                )
                                            }
                                            NavTab.SAVES -> {
                                                SavesScreen(
                                                    savesResponse = savesResponse,
                                                    appLanguage = appLanguage
                                                )
                                            }
                                            NavTab.ACCOUNT -> {
                                                AccountScreen(
                                                    userAccount = currentUser,
                                                    appLanguage = appLanguage,
                                                    onLanguageChange = { lang -> viewModel.setLanguage(lang) },
                                                    onSaveUsernameClick = { newUsername -> viewModel.updateUsername(newUsername) },
                                                    onSaveCountryClick = { countryCode -> viewModel.updateCountry(countryCode) },
                                                    onSaveProfileImageClick = { base64 -> viewModel.updateProfileImage(base64) },
                                                    onSettingsClick = { currentSubScreen = SubScreen.SETTINGS },
                                                    onLogoutClick = { viewModel.logout() }
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}