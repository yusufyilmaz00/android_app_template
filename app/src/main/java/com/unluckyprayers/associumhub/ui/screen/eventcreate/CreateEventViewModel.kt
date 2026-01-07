package com.unluckyprayers.associumhub.ui.screen.eventcreate

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateEventViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(CreateEventUiState())
    val uiState: StateFlow<CreateEventUiState> = _uiState.asStateFlow()

    // --- Kullanıcı Girdilerini Güncelleme Fonksiyonları ---

    fun onTitleChange(newValue: String) {
        _uiState.update { it.copy(title = newValue) }
    }

    fun onDateChange(newValue: String) {
        _uiState.update { it.copy(date = newValue) }
    }

    fun onTimeChange(newValue: String) {
        _uiState.update { it.copy(time = newValue) }
    }

    fun onLocationChange(newValue: String) {
        _uiState.update { it.copy(location = newValue) }
    }

    fun onDescriptionChange(newValue: String) {
        _uiState.update { it.copy(description = newValue) }
    }

    fun onImageSelected(uri: Uri?) {
        _uiState.update { it.copy(selectedImageUri = uri) }
    }

    // --- Kaydetme İşlemi ---

    fun postEvent() {
        val currentState = _uiState.value

        // Basit validasyon
        if (!currentState.isFormValid) {
            _uiState.update { it.copy(errorMessage = "Please fill in all required fields.") }
            return
        }

        viewModelScope.launch {
            // 1. Loading başlat
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            // 2. Network çağrısını simüle et (2 saniye bekle)
            delay(2000)

            // 3. Başarılı sonuç
            _uiState.update {
                it.copy(
                    isLoading = false,
                    isSuccess = true
                )
            }
        }
    }

    // Hata mesajını temizleme (örneğin Snackbar gösterildikten sonra)
    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}