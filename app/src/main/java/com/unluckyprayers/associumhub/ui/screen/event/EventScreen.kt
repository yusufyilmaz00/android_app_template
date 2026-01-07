package com.unluckyprayers.associumhub.ui.screen.event

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.unluckyprayers.associumhub.ui.navigation.Routes


@Composable
fun EventScreen(
    viewModel: EventViewModel = hiltViewModel(),
    navController: NavController,
    onBackClick: () -> Unit = {},
    onAddEventClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    
    // CREATE_EVENT'ten dönüldüğünde events'i yenile
    // Navigation back stack'inde CREATE_EVENT'ten MODERATOR_EVENT'e dönüldüğünü kontrol et
    LaunchedEffect(navBackStackEntry) {
        // Her MODERATOR_EVENT route'una dönüldüğünde refreshEvents() çağrılır
        // refreshEvents() içinde lastLoadedClubId null yapıldığı için her zaman yeniden yüklenir
        viewModel.refreshEvents()
    }

    EventUi(
        uiState = uiState,
        onBackClick = onBackClick,
        onAddEventClick = onAddEventClick
    )
}
