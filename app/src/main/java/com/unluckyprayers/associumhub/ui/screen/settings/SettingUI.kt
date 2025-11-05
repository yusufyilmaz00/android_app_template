package com.unluckyprayers.associumhub.ui.screen.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.unluckyprayers.associumhub.R

@Composable
fun SettingsUI(
    state: SettingState,
    onLanguageSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.drawer_item_settings),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        LanguageRowSelector(
            state = state,
            onLanguageSelected = onLanguageSelected
        )
    }
}

@Composable
private fun LanguageRowSelector(
    state: SettingState,
    onLanguageSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val currentLanguageName = state.supportedLanguages.find { it.code == state.currentLanguageCode }?.name ?: ""

    // 1. Etiket ve Seçim kutusunu aynı satırda hizalamak için Row kullanıyoruz
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween // Elemanları iki uca yaslar
    ) {
        // "Uygulama Dili" Etiketi
        Text(
            text = stringResource(id = R.string.settings_language_label),
            style = MaterialTheme.typography.titleMedium
        )

        // 2. Açılır menüyü içeren ve tıklanabilir olan Box
        Box {
            // Tıklanabilir, kenarlıklı kutu
            Row(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = MaterialTheme.shapes.small
                    )
                    .clickable { expanded = true }
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = currentLanguageName)
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Select Language"
                )
            }

            // 3. Açılır Menü
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                // Genişliği tetikleyici kutu ile aynı yapmak için .wrapContentWidth()
                modifier = Modifier.wrapContentWidth()
            ) {
                state.supportedLanguages.forEach { language ->
                    DropdownMenuItem(
                        text = { Text(language.name) },
                        onClick = {
                            onLanguageSelected(language.code)
                            expanded = false
                        },
                        // Seçili öğeyi vurgulamak için arka planı renklendirelim
                        modifier = Modifier
                            .background(
                                if (language.code == state.currentLanguageCode) {
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                                } else {
                                    Color.Transparent // Arka plan şeffaf
                                }
                            )
                    )
                }
            }
        }
    }
}