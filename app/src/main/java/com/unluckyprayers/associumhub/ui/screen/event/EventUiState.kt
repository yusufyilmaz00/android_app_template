package com.unluckyprayers.associumhub.ui.screen.event

import com.unluckyprayers.associumhub.domain.model.event.EventUiModel

data class EventUiState(
    val isLoading: Boolean = false,
    val events: List<EventUiModel> = emptyList(),
    val errorMessage: String? = null
)