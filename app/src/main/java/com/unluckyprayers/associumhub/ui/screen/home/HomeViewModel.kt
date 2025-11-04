package com.unluckyprayers.associumhub.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unluckyprayers.associumhub.data.repository.SystemMessageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: SystemMessageRepository
) : ViewModel() {
    init {
        viewModelScope.launch {
            repository.initializeSystemMessages()
        }
    }

    val uiState: StateFlow<HomeState> = repository.getLatestMessage()
        .map { entity ->
            if (entity != null) {
                HomeState(systemMessage = entity.messageText, isLoading = false)
            } else {
                HomeState(systemMessage = "Mesaj yükleniyor...", isLoading = true)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeState()
        )
}