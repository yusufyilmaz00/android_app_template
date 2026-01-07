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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Logout
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
    onLanguageSelected: (String) -> Unit,
    onLogoutClick: () -> Unit
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

        androidx.compose.foundation.layout.Spacer(modifier = androidx.compose.ui.Modifier.height(32.dp))

        LogoutButton(onClick = onLogoutClick)
    }
}


@Composable
private fun LanguageRowSelector(
    state: SettingState,
    onLanguageSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val currentLanguageName = state.supportedLanguages.find { it.code == state.currentLanguageCode }?.name ?: ""

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.settings_language_label),
            style = MaterialTheme.typography.titleMedium
        )

        Box {
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

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.wrapContentWidth()
            ) {
                state.supportedLanguages.forEach { language ->
                    DropdownMenuItem(
                        text = { Text(language.name) },
                        onClick = {
                            onLanguageSelected(language.code)
                            expanded = false
                        },
                        modifier = Modifier
                            .background(
                                if (language.code == state.currentLanguageCode) {
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                                } else {
                                    Color.Transparent
                                }
                            )
                    )
                }
            }
        }
    }
}

@androidx.compose.runtime.Composable
private fun LogoutButton(onClick: () -> Unit) {
    androidx.compose.material3.Button(
        onClick = onClick,
        modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = androidx.compose.material3.MaterialTheme.colorScheme.error,
            contentColor = androidx.compose.material3.MaterialTheme.colorScheme.onError
        )
    ) {
        androidx.compose.material3.Icon(
            imageVector = Icons.Default.Logout,
            contentDescription = null // Buton metni zaten açıklayıcı
        )
        androidx.compose.foundation.layout.Spacer(modifier = androidx.compose.ui.Modifier.padding(horizontal = 4.dp))
        androidx.compose.material3.Text(
            text = androidx.compose.ui.res.stringResource(id = R.string.settings_logout_button),
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
        )
    }
}