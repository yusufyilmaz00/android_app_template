package com.unluckyprayers.associumhub.ui.screen.eventcreate

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CreateEventScreen(
    viewModel: CreateEventViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Sonuç geldiğinde toast göster ve gerekirse geri dön
    LaunchedEffect(uiState.result) {
        when (val result = uiState.result) {
            is CreateEventResult.Success -> {
                Toast.makeText(context, "Başarıyla etkinlik oluşturuldu!", Toast.LENGTH_LONG).show()
                viewModel.clearResult()
                onNavigateBack()
            }
            is CreateEventResult.SuccessWithoutImage -> {
                Toast.makeText(context, "Etkinlik oluşturuldu, resim yüklenemedi", Toast.LENGTH_LONG).show()
                viewModel.clearResult()
                onNavigateBack()
            }
            is CreateEventResult.Error -> {
                Toast.makeText(context, "Hata: ${result.message}", Toast.LENGTH_LONG).show()
                viewModel.clearResult()
            }
            null -> { /* Henüz sonuç yok */ }
        }
    }

    // Görsel Seçici (Gallery Launcher)
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.onImageSelected(uri)
    }

    CreateEventUi(
        uiState = uiState,
        onTitleChange = viewModel::onTitleChange,
        onDateChange = viewModel::onDateChange,
        onTimeChange = viewModel::onTimeChange,
        onLocationChange = viewModel::onLocationChange,
        onDescriptionChange = viewModel::onDescriptionChange,
        onImageClick = { imagePickerLauncher.launch("image/*") },
        onCancelClick = onNavigateBack,
        onPostClick = viewModel::postEvent
    )
}
