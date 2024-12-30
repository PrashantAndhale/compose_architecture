package com.example.bottomnavigationandbottomsheet.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import com.example.ui.theme.AppTypography

private val lightScheme = lightColorScheme(
    primary = primaryLight,                  // Primary brand color for the light theme, used for buttons, app bar, etc.
    onPrimary = onPrimaryLight,              // Color for text and icons displayed on top of the primary color.
    secondary = secondaryLight,              // Secondary brand color for the light theme, used for accents, etc.
    onSecondary = onSecondaryLight,          // Color for text and icons displayed on top of the secondary color.
    error = errorLight,                      // Color for error states, used for error messages, error icons, etc.
    onError = onErrorLight,                  // Color for text and icons displayed on top of the error color.
    background = backgroundLight,            // Background color for the light theme.
    onBackground = onBackgroundLight,        // Color for text and icons displayed on top of the background color.
    surface = surfaceLight,                  // Surface color for components like cards, sheets, etc.
    onSurface = onSurfaceLight,
    surfaceTint =iconTintLight
)


private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    error = errorDark,
    onError = onErrorDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceTint = iconTintDark
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable() () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkScheme
        else -> lightScheme
    }
     SetStatusBarColor(darkTheme)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}

@Composable
fun SetStatusBarColor(darkTheme: Boolean) {
    val view = LocalView.current
    val activity = LocalContext.current as Activity
    SideEffect {
        val window = activity.window
          window.statusBarColor = if (darkTheme) Color.Black.toArgb() else Color.Black.toArgb()
    }
}


