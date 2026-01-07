package com.unluckyprayers.associumhub.ui.screen.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SettingScreen(
    viewModel: SettingsViewModel,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        val currentLanguageCode = AppCompatDelegate.getApplicationLocales()[0]?.toLanguageTag() ?: "en"
        println("DEBUG: setCurrentLanguage çağrılıyor - langCode: $currentLanguageCode")
        println("DEBUG SettingScreen: uiState.currentLanguageCode: ${uiState.currentLanguageCode}")
        viewModel.setCurrentLanguage(currentLanguageCode)
    }

    SettingsUI(
        state = uiState,
        onLanguageSelected = { newLangCode ->
            println("DEBUG SettingsUI: onLanguageSelected çağrıldı - newLangCode: $newLangCode")
            viewModel.onLanguageChange(newLangCode)
        },
        onLogoutClick = { viewModel.onLogoutClick() }
    )
}