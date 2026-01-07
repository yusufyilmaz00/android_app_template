package com.unluckyprayers.associumhub.ui.screen.qrcode

data class QRCodeUiState(
    val qrCodeBitmap: android.graphics.Bitmap? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
