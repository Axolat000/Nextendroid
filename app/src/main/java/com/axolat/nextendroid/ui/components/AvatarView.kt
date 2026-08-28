package com.axolat.nextendroid.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.scale
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.axolat.nextendroid.data.model.GameDictionary
import com.axolat.nextendroid.ui.theme.NextendoOnlinePink
import com.axolat.nextendroid.ui.theme.NextendoSurfaceElevated

@Composable
fun AvatarView(
    username: String,
    avatarUrl: String? = null,
    size: Dp = 64.dp,
    showOnlineDot: Boolean = true,
    isOnline: Boolean = true,
    gameIconUrl: String? = null,
    modifier: Modifier = Modifier
) {
    val decodedBitmap = remember(avatarUrl) {
        if (!avatarUrl.isNullOrBlank() && (avatarUrl.startsWith("data:") || avatarUrl.length > 150)) {
            GameDictionary.decodeBase64ToBitmap(avatarUrl)
        } else null
    }

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        if (decodedBitmap != null) {
            Image(
                bitmap = decodedBitmap.asImageBitmap(),
                contentDescription = username,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .border(2.dp, NextendoSurfaceElevated, CircleShape)
            )
        } else if (!avatarUrl.isNullOrBlank()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(avatarUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = username,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .border(2.dp, NextendoSurfaceElevated, CircleShape)
            )
        } else {
            // Stylized Fallback Avatar with Gradient
            val initial = username.firstOrNull()?.uppercase() ?: "N"
            val gradientColors = when ((username.hashCode() and 0x7FFFFFFF) % 5) {
                0 -> listOf(Color(0xFFE53E3E), Color(0xFF3182CE))
                1 -> listOf(Color(0xFF805AD5), Color(0xFFD53F8C))
                2 -> listOf(Color(0xFF319795), Color(0xFF2B6CB0))
                3 -> listOf(Color(0xFFDD6B20), Color(0xFFE53E3E))
                else -> listOf(Color(0xFF3182CE), Color(0xFF805AD5))
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(Brush.linearGradient(gradientColors))
                    .border(2.dp, NextendoSurfaceElevated, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = initial,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = (size.value * 0.45f).sp
                )
            }
        }

        // Online dot (Pink in Nextendo UI) — gentle pulse to draw the eye
        if (showOnlineDot && isOnline) {
            val pulseTransition = rememberInfiniteTransition(label = "onlineDotPulse")
            val pulseScale by pulseTransition.animateFloat(
                initialValue = 1f,
                targetValue = 1.18f,
                animationSpec = infiniteRepeatable(
                    animation = tween(900),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "onlineDotPulseScale"
            )
            Box(
                modifier = Modifier
                    .size(size * 0.28f)
                    .align(Alignment.BottomEnd)
                    .scale(pulseScale)
                    .background(NextendoOnlinePink, CircleShape)
                    .border(2.dp, Color(0xFF0C0E14), CircleShape)
            )
        }

        // Optional Mini Game Badge
        if (!gameIconUrl.isNullOrEmpty()) {
            Box(
                modifier = Modifier
                    .size(size * 0.35f)
                    .align(Alignment.BottomStart)
                    .clip(CircleShape)
                    .background(NextendoSurfaceElevated)
                    .border(1.dp, Color.White.copy(alpha = 0.5f), CircleShape)
            ) {
                AsyncImage(
                    model = gameIconUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
