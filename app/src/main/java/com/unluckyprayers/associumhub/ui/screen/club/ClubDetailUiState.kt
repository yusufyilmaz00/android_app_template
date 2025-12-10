package com.unluckyprayers.associumhub.ui.screen.club

import com.unluckyprayers.associumhub.domain.model.club.Club

data class ClubDetailUiState(
    val isLoading: Boolean = true,
    val club: Club? = null,
    val error: String? = null
)