package com.unluckyprayers.associumhub.ui.screen.search

import com.unluckyprayers.associumhub.domain.model.SectorUiModel
import com.unluckyprayers.associumhub.domain.model.club.SearchClubUiModel

data class SearchClubUiState(
    val searchQuery: String = "",
    val sectors: List<SectorUiModel> =emptyList(),
    val searchResults: List<SearchClubUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)