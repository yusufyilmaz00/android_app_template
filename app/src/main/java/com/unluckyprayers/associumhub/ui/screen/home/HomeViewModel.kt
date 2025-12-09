package com.unluckyprayers.associumhub.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.unluckyprayers.associumhub.data.repository.SystemMessageRepository
import com.unluckyprayers.associumhub.domain.usecase.GetClubsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getClubsUseCase: GetClubsUseCase
) : ViewModel() {

    val clubs = getClubsUseCase()
        .cachedIn(viewModelScope)
}