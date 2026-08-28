package com.axolat.nextendroid.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PersonRemove
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.axolat.nextendroid.data.model.Friend
import com.axolat.nextendroid.data.model.GameDictionary
import com.axolat.nextendroid.data.model.PlayHistoryItem
import com.axolat.nextendroid.ui.components.AvatarView
import com.axolat.nextendroid.ui.theme.*

@Composable
fun FriendDetailScreen(
    friend: Friend,
    playHistory: List<PlayHistoryItem>,
    appLanguage: AppLanguage = AppLanguage.FR,
    onBackClick: () -> Unit,
    onFavoriteToggle: (Long) -> Unit,
    onRemoveFriend: (Long) -> Unit = {},
    onBlockFriend: (Long) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val isOnline = (friend.presence?.status ?: 0) > 0
    val gameName = friend.presence?.resolvedGameName ?: Strings.onlineText(appLanguage)
    val appDetail = friend.presence?.appDetail ?: "Ryujinx"
    val isFavorite = friend.isFavorite || friend.favorite
    val gameCoverUrl = if (isOnline && !friend.presence?.appId.isNullOrEmpty()) {
        GameDictionary.getGameCoverUrl(friend.presence?.appId)
    } else null

    var menuExpanded by remember { mutableStateOf(false) }
    var confirmRemove by remember { mutableStateOf(false) }
    var confirmBlock by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(NextendoDarkBackground)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Top Navigation Bar
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = NextendoTextPrimary
                    )
                }

                Text(
                    text = Strings.tabFriends(appLanguage),
                    color = NextendoTextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )

                IconButton(onClick = { onFavoriteToggle(friend.pid) }) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Star else Icons.Outlined.StarOutline,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) NextendoBoosterPink else NextendoTextSecondary,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Box {
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = null,
                            tint = NextendoTextSecondary
                        )
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false },
                        modifier = Modifier.background(NextendoSurfaceElevated)
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = Strings.removeFriendAction(appLanguage), color = NextendoTextPrimary) },
                            leadingIcon = { Icon(imageVector = Icons.Filled.PersonRemove, contentDescription = null, tint = NextendoTextPrimary) },
                            onClick = {
                                menuExpanded = false
                                confirmRemove = true
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = Strings.blockFriendAction(appLanguage), color = MaterialTheme.colorScheme.error) },
                            leadingIcon = { Icon(imageVector = Icons.Filled.Block, contentDescription = null, tint = MaterialTheme.colorScheme.error) },
                            onClick = {
                                menuExpanded = false
                                confirmBlock = true
                            }
                        )
                    }
                }
            }
        }

        // Profile Header Card
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(NextendoSurfaceCard)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AvatarView(
                    username = friend.displayUsername,
                    avatarUrl = friend.formattedAvatarUrl,
                    size = 96.dp,
                    showOnlineDot = isOnline,
                    isOnline = isOnline,
                    gameIconUrl = gameCoverUrl
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = friend.displayUsername,
                    color = NextendoTextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = friend.friendCode.ifBlank { "SW-0833-9881-2231" },
                    color = NextendoTextSecondary,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Best Friend Button Pill
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (isFavorite) NextendoSurfaceElevated else NextendoDarkBackground)
                        .clickable { onFavoriteToggle(friend.pid) }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Star else Icons.Outlined.StarOutline,
                            contentDescription = null,
                            tint = if (isFavorite) NextendoBoosterPink else NextendoTextSecondary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = Strings.bestFriend(appLanguage),
                            color = if (isFavorite) NextendoBoosterPink else NextendoTextSecondary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }

        // Online Activity Card
        if (isOnline) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(NextendoSurfaceCard)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!gameCoverUrl.isNullOrEmpty()) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(gameCoverUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = gameName,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(56.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(NextendoSurfaceElevated),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = gameName.take(2).uppercase(),
                                color = NextendoPink,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(NextendoOnlinePink)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "${Strings.onlineText(appLanguage)} | $appDetail",
                                color = NextendoOnlinePink,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = gameName,
                            color = NextendoTextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }

        // Play History Section Header
        item {
            Text(
                text = Strings.playHistoryHeader(appLanguage),
                color = NextendoTextSecondary,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                letterSpacing = 0.5.sp,
                modifier = Modifier.padding(top = 16.dp, bottom = 12.dp)
            )
        }

        // History Items
        items(playHistory) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(NextendoSurfaceCard)
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val coverUrl = GameDictionary.getGameCoverUrl(item.resolvedTitleId)

                if (!coverUrl.isNullOrEmpty()) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(coverUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = item.resolvedName,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(NextendoSurfaceElevated),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = item.resolvedName.take(2).uppercase(),
                            color = NextendoTextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Text(
                        text = item.resolvedName,
                        color = NextendoTextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = item.playedTime ?: "A joué récemment",
                        color = NextendoTextSecondary,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }

    if (confirmRemove) {
        AlertDialog(
            onDismissRequest = { confirmRemove = false },
            title = { Text(text = Strings.removeFriendAction(appLanguage)) },
            text = { Text(text = Strings.confirmRemoveFriendDesc(appLanguage)) },
            confirmButton = {
                TextButton(onClick = {
                    confirmRemove = false
                    onRemoveFriend(friend.pid)
                    onBackClick()
                }) { Text(text = Strings.removeFriendAction(appLanguage), color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = {
                TextButton(onClick = { confirmRemove = false }) { Text(text = Strings.cancelButton(appLanguage)) }
            }
        )
    }

    if (confirmBlock) {
        AlertDialog(
            onDismissRequest = { confirmBlock = false },
            title = { Text(text = Strings.blockFriendAction(appLanguage)) },
            text = { Text(text = Strings.confirmBlockFriendDesc(appLanguage)) },
            confirmButton = {
                TextButton(onClick = {
                    confirmBlock = false
                    onBlockFriend(friend.pid)
                    onBackClick()
                }) { Text(text = Strings.blockFriendAction(appLanguage), color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = {
                TextButton(onClick = { confirmBlock = false }) { Text(text = Strings.cancelButton(appLanguage)) }
            }
        )
    }
}
