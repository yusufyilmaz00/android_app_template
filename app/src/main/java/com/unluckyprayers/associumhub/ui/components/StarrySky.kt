package com.unluckyprayers.associumhub.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlin.math.min
import kotlin.random.Random
import com.unluckyprayers.associumhub.ui.theme.*

private data class Star(
    val pos: Offset,
    val radiusPx: Float,
    val baseAlpha: Float,
    val group: Int // 0,1,2 – farklı twinkle hızına bağlayacağız
)

/**
 * Yıldızlı gökyüzü arka planı.
 * Çok hafif (~0 alloc) çalışır; istersen starCount ile oynayabilirsin.
 */
@Composable
fun StarrySkyBackground(
    modifier: Modifier = Modifier,
    starCount: Int = 140,
    seed: Int = 1337
) {
    val density = LocalDensity.current

    // 3 farklı "twinkle" kanalı; yıldızlar bu kanallara bölünüyor
    val anim = rememberInfiniteTransition(label = "twinkle")
    val tw1 = anim.animateFloat(
        initialValue = 0.6f, targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2600, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "tw1"
    ).value
    val tw2 = anim.animateFloat(
        initialValue = 0.5f, targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3400, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "tw2"
    ).value
    val tw3 = anim.animateFloat(
        initialValue = 0.7f, targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(4200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "tw3"
    ).value

    // This is the key change: The 'stars' list is now remembered in the composable scope.
    val stars = remember(starCount, seed, density) {
        val rnd = Random(seed)
        List(starCount) {
            val rDp = when (rnd.nextInt(8)) {
                0 -> 2.0f    // nadir büyük yıldız
                1, 2 -> 1.6f
                else -> 1.2f
            }
            val rPx = with(density) { rDp.dp.toPx() }
            val base = 0.55f + rnd.nextFloat() * 0.4f // 0.55–0.95
            val g = rnd.nextInt(3)
            // We can't know the size (w, h) here, so we store relative positions
            Star(Offset(rnd.nextFloat(), rnd.nextFloat()), rPx, base, g)
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Canvas(Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            val center = Offset(w / 2f, h / 2f)

            // Arka plan uzay gradyanı
            drawRect(
                brush = Brush.radialGradient(
                    colors = listOf(
                        AssociumSurface, AssociumBackground, AssociumBackground
                    ),
                    center = center,
                    radius = min(w, h) * 0.9f
                ),
                size = size
            )

            // Nebula ışıması (görseldeki yıldız kutucuğuna benzer hafif parıltı)
            // A nebulaBrush function seems to be missing, so I'm commenting it out
            // If you have it defined elsewhere, you can uncomment it.
            /*
            drawRect(
                brush = nebulaBrush(
                    center = Offset(w * 0.47f, h * 0.43f),
                    radius = min(w, h) * 0.35f
                ),
                size = size
            )
            */

            // Yıldızları çiz
            stars.forEach { s ->
                val tw = when (s.group) { 0 -> tw1; 1 -> tw2; else -> tw3 }
                val a = (s.baseAlpha * tw).coerceIn(0f, 1f)
                // Çok küçük bir mavi/turkuaz renk varyasyonu
                val tint = if (s.group == 1) AssociumTertiary else Color.White
                // Calculate the absolute position from the relative one
                val absolutePos = Offset(s.pos.x * w, s.pos.y * h)
                drawCircle(color = tint.copy(alpha = a), radius = s.radiusPx, center = absolutePos)
            }
        }
    }
}
