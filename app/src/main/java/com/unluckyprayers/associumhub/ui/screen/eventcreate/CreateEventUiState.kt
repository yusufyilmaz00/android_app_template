package com.unluckyprayers.associumhub.ui.screen.eventcreate

import android.net.Uri

data class CreateEventUiState(
    val title: String = "",
    val date: String = "",
    val time: String = "",
    val location: String = "",
    val description: String = "",
    val selectedImageUri: Uri? = null, // Galeriden seçilen görsel
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,    // İşlem başarılı mı? (Sayfayı kapatmak için)
    val errorMessage: String? = null
) {
    // Tüm alanlar dolu mu kontrolü (Butonu aktif/pasif yapmak için kullanılabilir)
    val isFormValid: Boolean
        get() = title.isNotBlank() && date.isNotBlank() && location.isNotBlank()
}