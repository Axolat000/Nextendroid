package com.axolat.nextendroid.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.axolat.nextendroid.ui.theme.*

data class FriendAvatarInfo(
    val username: String,
    val avatarUrl: String?
)

@Composable
fun GameCard(
    title: String,
    friendsPlayedCount: Int,
    coverUrl: String?,
    appLanguage: AppLanguage = AppLanguage.FR,
    friendAvatars: List<FriendAvatarInfo> = emptyList(),
    modifier: Modifier = Modifier
) {
    val subtitleText = if (friendAvatars.isNotEmpty()) {
        Strings.friendsPlayedText(appLanguage, friendsPlayedCount)
    } else {
        Strings.playersCountText(appLanguage, friendsPlayedCount)
    }

    Column(
        modifier = modifier
            .width(140.dp)
            .padding(end = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .width(140.dp)
                .height(180.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF222838), Color(0xFF171B26))
                    )
                )
        ) {
            if (!coverUrl.isNullOrBlank()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(coverUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                // Fallback styled layout when cover art is unavailable
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = title,
                        color = NextendoTextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            // Bottom subtle dark gradient scrim for text/avatar contrast
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f))
                        )
                    )
            )

            // Friend Avatar Overlays (renders actual PPs of friends)
            if (friendAvatars.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    friendAvatars.take(4).forEachIndexed { index, info ->
                        Box(
                            modifier = Modifier
                                .offset(x = (-6 * index).dp)
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF171B26))
                        ) {
                            AvatarView(
                                username = info.username,
                                avatarUrl = info.avatarUrl,
                                size = 24.dp,
                                showOnlineDot = false
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = title,
            color = NextendoTextPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = subtitleText,
            color = NextendoTextSecondary,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
