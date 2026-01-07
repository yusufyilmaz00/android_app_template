package com.unluckyprayers.associumhub.ui.screen.event

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun EventScreen(
    viewModel: EventViewModel = hiltViewModel(), // Hilt kullanıyorsan: hiltViewModel()
    onBackClick: () -> Unit = {},
    onAddEventClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    EventUi(
        uiState = uiState,
        onBackClick = onBackClick,
        onAddEventClick = onAddEventClick
    )
}
