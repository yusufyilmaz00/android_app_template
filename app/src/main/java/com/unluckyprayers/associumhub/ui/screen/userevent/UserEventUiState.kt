package com.unluckyprayers.associumhub.ui.screen.userevent

import com.unluckyprayers.associumhub.domain.model.event.EventUiModel

data class UserEventUiState(
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val events: List<EventUiModel> = emptyList(),
    val errorMessage: String? = null,
    val searchQuery: String = "",
    val currentPage: Int = 0,
    val hasMore: Boolean = true
)
