package com.unluckyprayers.associumhub.ui.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onClubClick: (Int) -> Unit
) {
    val clubsPaging = viewModel.clubs.collectAsLazyPagingItems()

    HomeUI(
        modifier = modifier,
        clubs = clubsPaging,
        onClubClick=onClubClick
    )
}