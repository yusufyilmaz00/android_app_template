package com.unluckyprayers.associumhub.ui.screen.eventcreate

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CreateEventScreen(
    viewModel: CreateEventViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    // Başarılı olursa sayfayı kapat
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onNavigateBack()
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