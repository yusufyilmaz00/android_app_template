package com.unluckyprayers.associumhub.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Yıldızlı arka plan üzerinde metni/icerigi öne çıkarmak için
 * “cam panel” hissi veren hafif saydam kart.
 */
@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(16.dp),
    shadowElevation: Dp = 12.dp,
    startAlpha: Float = 0.28f,   // üst kısım saydamlığı
    endAlpha: Float = 0.18f,     // alt kısım saydamlığı
    borderAlpha: Float = 0.08f,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    content: @Composable () -> Unit
) {
    val surface = MaterialTheme.colorScheme.surface
    val onSurface = MaterialTheme.colorScheme.onSurface
    val primary = MaterialTheme.colorScheme.primary

    Box(
        modifier = modifier
            .shadow(
                elevation = shadowElevation,
                shape = shape,
                ambientColor = primary.copy(alpha = 0.25f),
                spotColor = primary.copy(alpha = 0.25f)
            )
            .clip(shape)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        surface.copy(alpha = startAlpha),
                        surface.copy(alpha = endAlpha)
                    )
                )
            )
            .border(width = 1.dp, color = onSurface.copy(alpha = borderAlpha), shape = shape)
            .padding(contentPadding)
    ) {
        content()
    }
}
