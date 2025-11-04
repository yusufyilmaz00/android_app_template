package com.unluckyprayers.associumhub.ui.screen.template

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.unluckyprayers.associumhub.ui.components.StarrySkyBackground

@Composable
fun TemplateUI() {
    Box(modifier = Modifier.fillMaxSize()) {
        // 1) Arka plan katmanı
        StarrySkyBackground(
            modifier = Modifier.matchParentSize() // tüm ekranı kapla
        )

        // 2) İçerik katmanı
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Template UI",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}
