package com.unluckyprayers.associumhub.ui.screen.search

import com.unluckyprayers.associumhub.domain.model.club.ClubItem
import com.unluckyprayers.associumhub.domain.model.SectorUiModel

data class SearchUIState(
    val searchQuery: String = "",
    val sectors: List<SectorUiModel> = emptyList(),
    val searchResults: List<ClubItem> = emptyList(),
    val isLoading: Boolean = false
)