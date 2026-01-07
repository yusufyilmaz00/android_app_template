package com.unluckyprayers.associumhub.ui.screen.eventcreate

import android.net.Uri

// Etkinlik oluşturma sonuç türleri
sealed class CreateEventResult {
    object Success : CreateEventResult()                    // Her şey başarılı
    object SuccessWithoutImage : CreateEventResult()        // Etkinlik kaydedildi ama resim yüklenemedi
    data class Error(val message: String) : CreateEventResult() // Her şey başarısız
}

data class CreateEventUiState(
    val title: String = "",
    val date: String = "",
    val time: String = "",
    val location: String = "",
    val description: String = "",
    val selectedImageUri: Uri? = null, // Galeriden seçilen görsel
    val isLoading: Boolean = false,
    val isUploadingImage: Boolean = false, // Fotoğraf yükleniyor mu?
    val result: CreateEventResult? = null, // İşlem sonucu (toast için)
    val errorMessage: String? = null
) {
    // Tüm alanlar dolu mu kontrolü (Butonu aktif/pasif yapmak için kullanılabilir)
    val isFormValid: Boolean
        get() = title.isNotBlank() && date.isNotBlank() && location.isNotBlank()
    
    // Sayfa kapanmalı mı?
    val shouldNavigateBack: Boolean
        get() = result is CreateEventResult.Success || result is CreateEventResult.SuccessWithoutImage
}
