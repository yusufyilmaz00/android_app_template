package com.unluckyprayers.associumhub.ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.unluckyprayers.associumhub.ui.components.StarrySkyBackground

@Composable
fun HomeUI(
    modifier: Modifier = Modifier,
    state: HomeState
) {
    Box(modifier = modifier.fillMaxSize()) {
        // 1) Yıldızlı gökyüzü arka planı
        StarrySkyBackground(modifier = Modifier.matchParentSize())

        // 2) İsteğe bağlı: hafif gradient overlay (headerBrush)
        Box(
            modifier = Modifier
                .matchParentSize()
                .alpha(0.25f) // parıltıyı çok hafif ver
        )

        // 3) İçerik
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                Text(
                    text = state.systemMessage,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}
