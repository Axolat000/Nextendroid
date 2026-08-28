package com.axolat.nextendroid.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.axolat.nextendroid.data.model.Friend
import com.axolat.nextendroid.data.model.GameDictionary
import com.axolat.nextendroid.data.model.UserAccount
import com.axolat.nextendroid.ui.components.AvatarView
import com.axolat.nextendroid.ui.components.FriendAvatarInfo
import com.axolat.nextendroid.ui.components.GameCard
import com.axolat.nextendroid.ui.theme.*

@Composable
fun HomeScreen(
    currentUser: UserAccount?,
    friends: List<Friend>,
    friendsPlayedGames: Map<String, List<Friend>> = emptyMap(),
    onlineCounts: Map<String, Int>,
    appLanguage: AppLanguage,
    onFriendClick: (Friend) -> Unit,
    onSeeAllClick: () -> Unit,
    onRefreshClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val onlineFriends = friends.filter { (it.presence?.status ?: 0) > 0 }
    val onlineCount = onlineFriends.size
    val totalOnNextendo = onlineCounts.values.sum().let { if (it > 0) it else 53 }
    val dynamicGreeting = Strings.greeting(appLanguage)

    // Group ALL friends (online or offline) by the game they have played at least once from API history map
    val rawGamesMap = if (friendsPlayedGames.isNotEmpty()) {
        friendsPlayedGames
    } else {
        friends.filter { !it.presence?.appId.isNullOrEmpty() }
            .groupBy { GameDictionary.getCanonicalTitleId(it.presence?.appId) }
    }

    // Filter strictly for Nextendo officially supported games only
    val gamesMap = rawGamesMap.filterKeys { GameDictionary.isOfficialNextendoGame(it) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(NextendoDarkBackground)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        // App Header Title with Refresh Action
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ) {
                Text(
                    text = "Nextendo",
                    color = NextendoTextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.Center)
                )

                IconButton(
                    onClick = onRefreshClick,
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = "Refresh data",
                        tint = NextendoTextPrimary,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }

        // Greeting Section
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AvatarView(
                    username = currentUser?.displayUsername ?: "Axolat",
                    avatarUrl = GameDictionary.getAvatarUrl(currentUser?.image, currentUser?.avatar),
                    size = 56.dp,
                    showOnlineDot = false
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = dynamicGreeting,
                        color = NextendoTextSecondary,
                        fontSize = 14.sp
                    )
                    Text(
                        text = currentUser?.displayUsername ?: "Axolat",
                        color = NextendoTextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                }
            }
        }

        // ONLINE Section Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = Strings.onlineHeader(appLanguage),
                    color = NextendoTextSecondary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(NextendoSurfaceElevated)
                        .padding(horizontal = 10.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "$onlineCount",
                        color = NextendoPink,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
        }

        // Online Friends Horizontal List
        item {
            if (onlineFriends.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(NextendoSurfaceCard)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = Strings.noFriendsOnline(appLanguage),
                        color = NextendoTextSecondary,
                        fontSize = 13.sp
                    )
                }
            } else {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(bottom = 20.dp)
                ) {
                    items(onlineFriends) { friend ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .width(68.dp)
                                .clickable { onFriendClick(friend) }
                        ) {
                            AvatarView(
                                username = friend.displayUsername,
                                avatarUrl = friend.formattedAvatarUrl,
                                size = 56.dp,
                                showOnlineDot = true,
                                isOnline = true,
                                gameIconUrl = GameDictionary.getGameCoverUrl(friend.presence?.appId)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = friend.displayUsername,
                                color = NextendoTextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = friend.presence?.resolvedGameName ?: Strings.onlineText(appLanguage),
                                color = NextendoTextSecondary,
                                fontSize = 10.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }

        // À QUOI JOUENT VOS AMIS Section Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = Strings.friendsPlayingHeader(appLanguage),
                    color = NextendoTextSecondary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    letterSpacing = 0.5.sp
                )
                Text(
                    text = Strings.seeAll(appLanguage),
                    color = NextendoPink,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable { onSeeAllClick() }
                )
            }
        }

        // Games played at least once by your friends (strictly official Nextendo games only)
        item {
            if (gamesMap.isNotEmpty()) {
                LazyRow(
                    modifier = Modifier.padding(bottom = 24.dp)
                ) {
                    items(gamesMap.entries.toList()) { (titleId, friendsWhoPlayed) ->
                        val gameTitle = GameDictionary.getGameName(titleId)
                        val coverUrl = GameDictionary.getGameCoverUrl(titleId)

                        GameCard(
                            title = gameTitle,
                            friendsPlayedCount = friendsWhoPlayed.size,
                            coverUrl = coverUrl,
                            appLanguage = appLanguage,
                            friendAvatars = friendsWhoPlayed.map { FriendAvatarInfo(it.displayUsername, it.formattedAvatarUrl) }
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(NextendoSurfaceCard)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Aucun historique de jeu d'amis pour le moment.",
                        color = NextendoTextSecondary,
                        fontSize = 13.sp
                    )
                }
            }
        }

        // ON NEXTENDO Section Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = Strings.onNextendoHeader(appLanguage),
                        color = NextendoTextSecondary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(NextendoSurfaceElevated)
                            .padding(horizontal = 10.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "$totalOnNextendo",
                            color = NextendoPink,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }

                IconButton(
                    onClick = onRefreshClick,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = "Refresh counts",
                        tint = NextendoPink,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        // ON NEXTENDO Ranking List Card
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(NextendoSurfaceCard)
                    .padding(vertical = 8.dp)
            ) {
                val gameList = if (onlineCounts.isNotEmpty()) {
                    onlineCounts.filterKeys { GameDictionary.isOfficialNextendoGame(it) }
                        .map { (key, count) -> GameDictionary.getGameName(key) to count }
                } else {
                    listOf(
                        "Super Smash Bros. Ultimate" to 58,
                        "Mario Kart 8 Deluxe" to 46,
                        "Splatoon 2" to 39,
                        "Super Mario Maker 2" to 6,
                        "Animal Crossing: New Horizons" to 5,
                        "Luigi's Mansion 3" to 5
                    )
                }

                gameList.forEachIndexed { index, (gameTitle, count) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = gameTitle,
                            color = NextendoTextPrimary,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "$count",
                            color = NextendoPink,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }

                    if (index < gameList.size - 1) {
                        HorizontalDivider(
                            color = NextendoDivider,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }
            }
        }
    }
}
