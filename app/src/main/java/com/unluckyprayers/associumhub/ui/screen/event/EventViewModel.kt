package com.unluckyprayers.associumhub.ui.screen.event

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unluckyprayers.associumhub.data.local.model.UserState
import com.unluckyprayers.associumhub.data.repository.AuthRepository
import com.unluckyprayers.associumhub.domain.usecase.GetClubEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val getClubEventsUseCase: GetClubEventsUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(EventUiState())
    val uiState: StateFlow<EventUiState> = _uiState.asStateFlow()

    init {
        // AuthState'i dinle ve clubId'yi al
        authRepository.userState
            .onEach { authState ->
                when (authState) {
                    is UserState.Success -> {
                        authState.clubId?.let { clubId ->
                            Log.d("EventViewModel", "Club ID: $clubId")
                            loadEvents(clubId)
                        } ?: run {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = "Kulüp bilgisi bulunamadı"
                                )
                            }
                        }
                    }
                    is UserState.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = authState.message
                            )
                        }
                    }
                    else -> {
                        // Loading veya Idle durumunda bir şey yapma
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun loadEvents(clubId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            getClubEventsUseCase(clubId)
                .onSuccess { events ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            events = events,
                            errorMessage = null
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            events = emptyList(),
                            errorMessage = exception.message ?: "Etkinlikler yüklenemedi"
                        )
                    }
                }
        }
    }

    fun refreshEvents() {
        viewModelScope.launch {
            when (val authState = authRepository.userState.value) {
                is UserState.Success -> {
                    authState.clubId?.let { clubId ->
                        loadEvents(clubId)
                    }
                }
                else -> {
                    _uiState.update {
                        it.copy(
                            errorMessage = "Kullanıcı bilgisi alınamadı"
                        )
                    }
                }
            }
        }
    }
}
