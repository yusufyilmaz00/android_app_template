package com.unluckyprayers.associumhub.ui.screen.userevent

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun UserEventScreen(
    viewModel: UserEventViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    UserEventUi(
        uiState = uiState,
        onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
        onClearSearch = { viewModel.onClearSearch() },
        onLoadMore = { viewModel.loadMoreEvents() }
    )
}
