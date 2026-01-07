package com.unluckyprayers.associumhub.ui.screen.eventcreate

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unluckyprayers.associumhub.domain.model.event.CreateEventParams
import com.unluckyprayers.associumhub.domain.usecase.CreateEventUseCase
import com.unluckyprayers.associumhub.domain.usecase.UploadEventPosterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CreateEventViewModel @Inject constructor(
    private val uploadEventPosterUseCase: UploadEventPosterUseCase,
    private val createEventUseCase: CreateEventUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateEventUiState())
    val uiState: StateFlow<CreateEventUiState> = _uiState.asStateFlow()

    // Yüklenen fotoğrafın URL'ini sakla
    private var uploadedImageUrl: String? = null
    private var imageUploadFailed = false

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
        // Fotoğraf seçildiğinde önceki yükleme URL'ini sıfırla
        uploadedImageUrl = null
        imageUploadFailed = false
    }

    // --- Fotoğraf Yükleme İşlemi ---

    private suspend fun uploadPosterIfNeeded(): Boolean {
        val imageUri = _uiState.value.selectedImageUri

        // Fotoğraf yoksa devam et (başarılı sayılır)
        if (imageUri == null) return true
        
        // Zaten yüklenmişse devam et
        if (uploadedImageUrl != null) return true

        _uiState.update { it.copy(isUploadingImage = true) }

        val result = uploadEventPosterUseCase(imageUri)

        return result.fold(
            onSuccess = { uploadResult ->
                uploadedImageUrl = uploadResult.url
                imageUploadFailed = false
                _uiState.update { it.copy(isUploadingImage = false) }
                true
            },
            onFailure = { _ ->
                imageUploadFailed = true
                uploadedImageUrl = null
                _uiState.update { it.copy(isUploadingImage = false) }
                // Resim yüklenemese bile devam et (etkinlik kaydedilecek)
                true
            }
        )
    }

    // Tarih formatını API için dönüştür (MMM dd, yyyy -> yyyy-MM-dd)
    private fun convertDateForApi(displayDate: String): String {
        return try {
            val inputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH)
            val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = inputFormat.parse(displayDate)
            date?.let { outputFormat.format(it) } ?: displayDate
        } catch (e: Exception) {
            displayDate
        }
    }

    // Saat formatını API için dönüştür (h:mm AM/PM -> HH:mm)
    private fun convertTimeForApi(displayTime: String): String {
        return try {
            val inputFormat = SimpleDateFormat("h:mm a", Locale.ENGLISH)
            val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val time = inputFormat.parse(displayTime)
            time?.let { outputFormat.format(it) } ?: displayTime
        } catch (e: Exception) {
            displayTime
        }
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
            _uiState.update { it.copy(isLoading = true, errorMessage = null, result = null) }

            // 2. Fotoğrafı yükle (varsa)
            uploadPosterIfNeeded()

            // 3. Event oluşturma API çağrısı
            val params = CreateEventParams(
                title = currentState.title,
                date = convertDateForApi(currentState.date),
                time = convertTimeForApi(currentState.time),
                location = currentState.location,
                description = currentState.description,
                imageUrl = uploadedImageUrl ?: "",
                clubId = "1" // TODO: Gerçek club ID'yi kullan
            )

            val createResult = createEventUseCase(params)

            createResult.fold(
                onSuccess = {
                    // Etkinlik başarıyla oluşturuldu
                    val result = if (imageUploadFailed && currentState.selectedImageUri != null) {
                        // Resim yüklenemedi ama etkinlik kaydedildi
                        CreateEventResult.SuccessWithoutImage
                    } else {
                        // Her şey başarılı
                        CreateEventResult.Success
                    }
                    
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            result = result
                        )
                    }
                },
                onFailure = { exception ->
                    // Etkinlik oluşturulamadı
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            result = CreateEventResult.Error(exception.message ?: "Bilinmeyen hata")
                        )
                    }
                }
            )
        }
    }

    // Hata mesajını temizleme
    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    // Sonucu temizleme (toast gösterildikten sonra)
    fun clearResult() {
        _uiState.update { it.copy(result = null) }
    }
}
