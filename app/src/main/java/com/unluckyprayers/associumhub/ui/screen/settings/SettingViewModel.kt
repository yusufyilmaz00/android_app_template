package com.unluckyprayers.associumhub.ui.screen.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(SettingState())
    val uiState: StateFlow<SettingState> = _uiState.asStateFlow()

    init {
        loadSettings()
    }

    private fun loadSettings() {
        val supported = listOf(
            Language("en", "English"),
            Language("tr", "Türkçe")
        )

        // Mevcut uygulama dilini al. Eğer ayarlanmamışsa null dönebilir.
        val currentLocaleTag = AppCompatDelegate.getApplicationLocales()[0]?.toLanguageTag() ?: "en"

        _uiState.update {
            it.copy(
                supportedLanguages = supported,
                currentLanguageCode = currentLocaleTag
            )
        }
    }

    // UI'dan gelen dil değiştirme isteğini işleyen fonksiyon
    fun onLanguageChange(newLanguageCode: String) {
        val appLocale = LocaleListCompat.forLanguageTags(newLanguageCode)
        AppCompatDelegate.setApplicationLocales(appLocale)

        // UI state'ini de güncelleyelim ki arayüz anında tepki versin (isteğe bağlı ama iyi bir pratik)
        _uiState.update { it.copy(currentLanguageCode = newLanguageCode) }
    }
}
