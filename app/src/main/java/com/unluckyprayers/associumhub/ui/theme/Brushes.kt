package com.unluckyprayers.associumhub.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val headerBrush: Brush = Brush.linearGradient(
    0f to AssociumSecondary.copy(alpha = 0.35f),
    1f to AssociumPrimary.copy(alpha = 0.35f)
)

// Gece gökyüzü (merkezde biraz daha açık, kenarlara kararan)
val skyBrush = Brush.radialGradient(
    colors = listOf(
        AssociumSurface,                  // merkez
        AssociumBackground,               // orta
        AssociumBackground.copy(alpha = .95f) // kenarlar
    ),
    center = Offset.Zero, // canvas'ta ortalanacak; StarrySky içinde override edeceğiz
    radius = 800f
)

// Hafif mavi-turkuaz “nebula” ışıması (opsiyonel)
fun nebulaBrush(center: Offset, radius: Float) = Brush.radialGradient(
    colors = listOf(
        AssociumPrimary.copy(alpha = .18f),
        Color.Transparent
    ),
    center = center,
    radius = radius
)
