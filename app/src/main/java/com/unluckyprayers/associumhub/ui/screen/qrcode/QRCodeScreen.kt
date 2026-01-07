package com.unluckyprayers.associumhub.ui.screen.qrcode

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun QRCodeScreen(
    eventId: String,
    eventTitle: String,
    imageUrl: String,
    viewModel: QRCodeViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(eventId) {
        viewModel.generateQRCode(eventId, eventTitle, imageUrl)
    }
    
    QRCodeUi(
        uiState = uiState,
        onBackClick = onBackClick,
        eventTitle = eventTitle
    )
}
