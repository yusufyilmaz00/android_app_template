package com.unluckyprayers.associumhub.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColors: ColorScheme = darkColorScheme(
    primary = AssociumPrimary,
    onPrimary = AssociumOnPrimary,
    primaryContainer = AssociumPrimaryContainer,
    onPrimaryContainer = AssociumOnPrimaryContainer,

    secondary = AssociumSecondary,
    onSecondary = AssociumOnSecondary,
    secondaryContainer = AssociumSecondaryContainer,
    onSecondaryContainer = AssociumOnSecondaryContainer,

    tertiary = AssociumTertiary,
    onTertiary = AssociumOnTertiary,
    tertiaryContainer = AssociumTertiaryContainer,
    onTertiaryContainer = AssociumOnTertiaryContainer,

    background = AssociumBackground,
    onBackground = AssociumOnBackground,
    surface = AssociumSurface,
    onSurface = AssociumOnSurface,
    surfaceVariant = AssociumSurfaceVariant,
    onSurfaceVariant = AssociumOnSurfaceVariant,
    outline = AssociumOutline,

    error = AssociumError,
    onError = AssociumOnError,
    errorContainer = AssociumErrorContainer,
    onErrorContainer = AssociumOnErrorContainer
)

private val LightColors: ColorScheme = lightColorScheme(
    primary = AssociumPrimary,
    onPrimary = AssociumOnPrimary,
    primaryContainer = AssociumOnPrimaryContainer,
    onPrimaryContainer = AssociumPrimaryContainer,

    secondary = AssociumSecondary,
    onSecondary = AssociumOnSecondary,
    secondaryContainer = AssociumOnSecondaryContainer,
    onSecondaryContainer = AssociumSecondaryContainer,

    tertiary = AssociumTertiary,
    onTertiary = AssociumOnTertiary,
    tertiaryContainer = AssociumOnTertiaryContainer,
    onTertiaryContainer = AssociumTertiaryContainer,

    background = Color(0xFFFFFFFF),
    onBackground = Color(0xFF0B1220),
    surface = Color(0xFFF8FAFF),
    onSurface = Color(0xFF0B1220),
    surfaceVariant = Color(0xFFE6ECF5),
    onSurfaceVariant = Color(0xFF405169),
    outline = Color(0xFF7B8797),

    error = AssociumError,
    onError = AssociumOnError,
    errorContainer = Color(0xFFFFE4E6),
    onErrorContainer = Color(0xFF3F0C0C)
)

@Composable
fun AssociumTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = MaterialTheme.typography, // şimdilik default
        shapes = MaterialTheme.shapes,
        content = content
    )
}