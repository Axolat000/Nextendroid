package com.axolat.nextendroid.ui.theme

import androidx.compose.ui.graphics.Color

val NextendoDarkBackground = Color(0xFF0C0E14)
val NextendoSurfaceCard = Color(0xFF171B26)
val NextendoSurfaceElevated = Color(0xFF222838)
val NextendoSurfaceVariant = Color(0xFF222838)
val NextendoBorder = Color(0xFF2E364A)
val NextendoDivider = Color(0xFF1F2535)

var NextendoPink = Color(0xFFFF2D75)
val NextendoPinkLight = Color(0xFFFF5E99)
val NextendoOnlinePink = Color(0xFFFF2D75)
val NextendoBoosterPink = Color(0xFFFF2D75)
val NextendoOnlineGreen = Color(0xFF10B981)

val NextendoTextPrimary = Color(0xFFFFFFFF)
val NextendoTextSecondary = Color(0xFF94A3B8)
val NextendoTextMuted = Color(0xFF64748B)

data class AccentColorOption(
    val id: String,
    val name: String,
    val color: Color
)

val accentColorOptions = listOf(
    AccentColorOption("nextendo", "Nextendo", Color(0xFF00C9A7)),
    AccentColorOption("encre", "Encre", Color(0xFF3B82F6)),
    AccentColorOption("prune", "Prune", Color(0xFF8B5CF6)),
    AccentColorOption("framboise", "Framboise", Color(0xFFFF2D75)),
    AccentColorOption("braise", "Braise", Color(0xFFFF6B00)),
    AccentColorOption("or", "Or", Color(0xFFFFC700)),
    AccentColorOption("menthe", "Menthe", Color(0xFF10B981)),
    AccentColorOption("corail", "Corail", Color(0xFFEF4444))
)