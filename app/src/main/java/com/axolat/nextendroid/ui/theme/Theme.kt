package com.axolat.nextendroid.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = NextendoPink,
    secondary = NextendoPinkLight,
    tertiary = NextendoSurfaceElevated,
    background = NextendoDarkBackground,
    surface = NextendoSurfaceCard,
    surfaceVariant = NextendoSurfaceVariant,
    onPrimary = NextendoTextPrimary,
    onSecondary = NextendoTextPrimary,
    onBackground = NextendoTextPrimary,
    onSurface = NextendoTextPrimary,
    onSurfaceVariant = NextendoTextSecondary
)

@Composable
fun NextendroidTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = NextendoDarkBackground.toArgb()
            window.navigationBarColor = NextendoDarkBackground.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}