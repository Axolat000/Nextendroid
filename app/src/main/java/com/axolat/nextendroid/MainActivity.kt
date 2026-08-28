package com.axolat.nextendroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
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
    SETTINGS,
    SESSIONS
}

enum class AuthScreen {
    LOGIN,
    REGISTER,
    FORGOT_PASSWORD,
    RESET_PASSWORD
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
                val networkStatus by viewModel.networkStatus.collectAsState()
                val registrationOpen by viewModel.registrationOpen.collectAsState()
                val usernameAvailable by viewModel.usernameAvailable.collectAsState()
                val sessions by viewModel.sessions.collectAsState()
                val sessionsLoading by viewModel.sessionsLoading.collectAsState()

                var currentTab by remember { mutableStateOf(NavTab.HOME) }
                var currentSubScreen by remember { mutableStateOf(SubScreen.NONE) }
                var authScreen by remember { mutableStateOf(AuthScreen.LOGIN) }

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
                        AnimatedContent(
                            targetState = authScreen,
                            transitionSpec = { fadeIn(tween(220)) togetherWith fadeOut(tween(150)) },
                            label = "auth_transition"
                        ) { screen ->
                            when (screen) {
                                AuthScreen.LOGIN -> LoginScreen(
                                    onLoginClick = { email, pw -> viewModel.login(email, pw) },
                                    isLoading = isLoading,
                                    errorMessage = loginError,
                                    appLanguage = appLanguage,
                                    onRegisterClick = {
                                        viewModel.clearLoginError()
                                        authScreen = AuthScreen.REGISTER
                                    },
                                    onForgotPasswordClick = {
                                        viewModel.clearLoginError()
                                        authScreen = AuthScreen.FORGOT_PASSWORD
                                    },
                                    onGuestLoginClick = { username -> viewModel.guestLogin(username) }
                                )
                                AuthScreen.REGISTER -> RegisterScreen(
                                    appLanguage = appLanguage,
                                    registrationOpen = registrationOpen,
                                    usernameAvailable = usernameAvailable,
                                    isLoading = isLoading,
                                    errorMessage = loginError,
                                    onUsernameChange = { username -> viewModel.checkUsernameAvailable(username) },
                                    onRegisterClick = { username, email, password, country ->
                                        viewModel.register(username, email, password, country)
                                    },
                                    onBackToLoginClick = {
                                        viewModel.clearLoginError()
                                        authScreen = AuthScreen.LOGIN
                                    }
                                )
                                AuthScreen.FORGOT_PASSWORD -> ForgotPasswordScreen(
                                    appLanguage = appLanguage,
                                    isLoading = isLoading,
                                    onSendResetLinkClick = { email, onResult -> viewModel.forgotPassword(email, onResult) },
                                    onBackToLoginClick = { authScreen = AuthScreen.LOGIN },
                                    onHaveCodeClick = { authScreen = AuthScreen.RESET_PASSWORD }
                                )
                                AuthScreen.RESET_PASSWORD -> ResetPasswordScreen(
                                    appLanguage = appLanguage,
                                    isLoading = isLoading,
                                    onResetClick = { token, newPassword, onResult -> viewModel.resetPassword(token, newPassword, onResult) },
                                    onBackToLoginClick = { authScreen = AuthScreen.LOGIN }
                                )
                            }
                        }
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
                                AnimatedContent(
                                    targetState = currentSubScreen,
                                    transitionSpec = {
                                        if (initialState == SubScreen.NONE && targetState != SubScreen.NONE) {
                                            // Pushing a subscreen: slide in from the right
                                            (slideInHorizontally(tween(280)) { it } + fadeIn(tween(280))) togetherWith
                                                (slideOutHorizontally(tween(280)) { -it / 4 } + fadeOut(tween(200)))
                                        } else if (targetState == SubScreen.NONE) {
                                            // Popping back: slide in from the left
                                            (slideInHorizontally(tween(280)) { -it / 4 } + fadeIn(tween(280))) togetherWith
                                                (slideOutHorizontally(tween(280)) { it } + fadeOut(tween(200)))
                                        } else {
                                            fadeIn(tween(200)) togetherWith fadeOut(tween(200))
                                        }
                                    },
                                    label = "subscreen_transition"
                                ) { subScreen ->
                                when (subScreen) {
                                    SubScreen.FRIEND_DETAIL -> {
                                        selectedFriend?.let { friend ->
                                            FriendDetailScreen(
                                                friend = friend,
                                                playHistory = playHistory,
                                                appLanguage = appLanguage,
                                                onBackClick = { currentSubScreen = SubScreen.NONE },
                                                onFavoriteToggle = { pid -> viewModel.toggleFavoriteFriend(pid) },
                                                onRemoveFriend = { pid -> viewModel.removeFriend(pid) },
                                                onBlockFriend = { pid -> viewModel.blockFriend(pid) }
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
                                    SubScreen.SESSIONS -> {
                                        LaunchedEffect(Unit) {
                                            viewModel.loadSessions()
                                        }
                                        SessionsScreen(
                                            sessions = sessions,
                                            isLoading = sessionsLoading,
                                            appLanguage = appLanguage,
                                            onBackClick = { currentSubScreen = SubScreen.NONE },
                                            onRevokeSession = { session -> viewModel.revokeSession(session.id, session.current) },
                                            onRevokeAll = { viewModel.revokeAllSessions() }
                                        )
                                    }
                                    SubScreen.NONE -> {
                                        AnimatedContent(
                                            targetState = currentTab,
                                            transitionSpec = {
                                                fadeIn(tween(220)) togetherWith fadeOut(tween(150))
                                            },
                                            label = "tab_transition"
                                        ) { tab ->
                                        when (tab) {
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
                                                    appLanguage = appLanguage,
                                                    onDeleteSave = { titleId, onResult -> viewModel.deleteSave(titleId, onResult) },
                                                    onDownloadSave = { titleId, fileName, onResult -> viewModel.downloadSave(titleId, fileName, onResult) }
                                                )
                                            }
                                            NavTab.ACCOUNT -> {
                                                AccountScreen(
                                                    userAccount = currentUser,
                                                    appLanguage = appLanguage,
                                                    onLanguageChange = { lang -> viewModel.setLanguage(lang) },
                                                    networkStatus = networkStatus,
                                                    onSaveUsernameClick = { newUsername -> viewModel.updateUsername(newUsername) },
                                                    onSaveCountryClick = { countryCode -> viewModel.updateCountry(countryCode) },
                                                    onSaveProfileImageClick = { base64 -> viewModel.updateProfileImage(base64) },
                                                    onSettingsClick = { currentSubScreen = SubScreen.SETTINGS },
                                                    onSessionsClick = { currentSubScreen = SubScreen.SESSIONS },
                                                    onResendVerificationClick = { onResult -> viewModel.resendVerification(onResult) },
                                                    onChangeEmailClick = { email, password, onResult -> viewModel.changeEmail(email, password, onResult) },
                                                    onDeleteAccountClick = { password, onResult -> viewModel.deleteAccount(password, onResult) },
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
    }
}