package com.unluckyprayers.associumhub.ui.screen.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SearchClubScreen(
    viewModel: SearchClubViewModel = hiltViewModel(),
    onClubClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    SearchUI(
        state = uiState,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onClearSearch = viewModel::onClearSearch,
        onFilterButtonClick = { },
        onSectorSelect = viewModel::onSectorToggle,
        onResultClick = onClubClick,
        onClearFilters = viewModel::onClearFilters
    )
}