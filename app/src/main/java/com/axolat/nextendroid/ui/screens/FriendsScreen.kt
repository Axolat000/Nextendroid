package com.axolat.nextendroid.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.axolat.nextendroid.data.model.Friend
import com.axolat.nextendroid.data.model.GameDictionary
import com.axolat.nextendroid.ui.components.AvatarView
import com.axolat.nextendroid.ui.theme.*

@Composable
fun FriendsScreen(
    friends: List<Friend>,
    appLanguage: AppLanguage = AppLanguage.FR,
    onFriendClick: (Friend) -> Unit,
    onAddFriendClick: () -> Unit,
    onRefreshClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val totalFriendsCount = friends.size

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(NextendoDarkBackground)
            .padding(horizontal = 16.dp)
    ) {
        // Top Bar Title with Refresh and Add Actions
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        ) {
            Text(
                text = Strings.tabFriends(appLanguage),
                color = NextendoTextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.Center)
            )

            Row(
                modifier = Modifier.align(Alignment.CenterEnd),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onRefreshClick) {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = "Refresh friends",
                        tint = NextendoTextPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                }

                IconButton(onClick = onAddFriendClick) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = Strings.addFriendTitle(appLanguage),
                        tint = NextendoTextPrimary,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }
        }

        // AMIS Pill Badge Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = Strings.amisHeader(appLanguage),
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
                    text = "$totalFriendsCount",
                    color = NextendoPink,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
        }

        // Friends Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 90.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(friends) { friend ->
                val isOnline = (friend.presence?.status ?: 0) > 0
                val gameCoverUrl = if (isOnline && !friend.presence?.appId.isNullOrEmpty()) {
                    GameDictionary.getGameCoverUrl(friend.presence?.appId)
                } else null

                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(NextendoSurfaceCard)
                        .clickable { onFriendClick(friend) }
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AvatarView(
                        username = friend.displayUsername,
                        avatarUrl = friend.formattedAvatarUrl,
                        size = 64.dp,
                        showOnlineDot = isOnline,
                        isOnline = isOnline,
                        gameIconUrl = gameCoverUrl
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = friend.displayUsername,
                        color = NextendoTextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = if (isOnline) (friend.presence?.resolvedGameName ?: Strings.onlineText(appLanguage)) else Strings.offlineText(appLanguage),
                        color = if (isOnline) NextendoTextSecondary else NextendoTextMuted,
                        fontSize = 11.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
