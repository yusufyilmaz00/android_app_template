package com.unluckyprayers.associumhub.ui.screen.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(SettingState())
    val uiState: StateFlow<SettingState> = _uiState.asStateFlow()

    private val _eventChannel = Channel<SettingsEvent>()
    val events = _eventChannel.receiveAsFlow()

    init {
        // ViewModel ilk oluşturulduğunda desteklenen dilleri yükle
        _uiState.update {
            it.copy(
                supportedLanguages = listOf(
                    Language("en", "English"),
                    Language("tr", "Türkçe")
                )
            )
        }
    }

    /**
     * NavGraph'tan gelen mevcut dil kodunu State'e yazar.
     */
    fun setCurrentLanguage(langCode: String) {
        _uiState.update { it.copy(currentLanguageCode = langCode) }
    }

    /**
     * Kullanıcı yeni bir dil seçtiğinde tetiklenir.
     */
    fun onLanguageChange(newLanguageCode: String) {
        println("DEBUG: onLanguageChange çağrıldı - newLanguageCode: $newLanguageCode")
        println("DEBUG: currentLanguageCode: ${_uiState.value.currentLanguageCode}")

        // Eğer zaten seçili olan dil tekrar seçilirse hiçbir şey yapma
        if (_uiState.value.currentLanguageCode == newLanguageCode) {
            println("DEBUG: Aynı dil seçildi, return ediliyor")
            return
        }

        println("DEBUG: State güncelleniyor ve event gönderiliyor")
        _uiState.update { it.copy(currentLanguageCode = newLanguageCode) }
        _eventChannel.trySend(SettingsEvent.ChangeLanguage(newLanguageCode))
    }

    fun onLogoutClick() {
        // Oturumu kapatma isteğini bir event olarak gönder.
        // Asıl çıkış işlemi ve navigasyon, bu event'i dinleyen üst katmanda (NavGraph/Activity) yapılacak.
        _eventChannel.trySend(SettingsEvent.Logout)
    }

    sealed class SettingsEvent {
        data class ChangeLanguage(val code: String) : SettingsEvent()
        data object Logout : SettingsEvent()
    }
}