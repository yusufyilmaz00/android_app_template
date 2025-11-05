package com.unluckyprayers.associumhub.ui.screen.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SettingScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    // ViewModel'den state'i topla
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // UI'ı çağır ve state ile event'leri bağla
    SettingsUI(
        state = uiState,
        onLanguageSelected = { newLangCode ->
            viewModel.onLanguageChange(newLangCode)
        }
    )
}
