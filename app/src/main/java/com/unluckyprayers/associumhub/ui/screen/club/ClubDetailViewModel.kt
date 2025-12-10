package com.unluckyprayers.associumhub.ui.screen.club

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unluckyprayers.associumhub.domain.usecase.GetClubByIdUseCase
import com.unluckyprayers.associumhub.ui.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ClubDetailViewModel @Inject constructor(
    private val getClubByIdUseCase: GetClubByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClubDetailUiState())
    val uiState = _uiState.asStateFlow()

    init {

        val clubId: Int? = savedStateHandle[Routes.CLUB_ID_ARG]
        if (clubId != null) {
            fetchClubDetails(clubId)
        } else {
            _uiState.update {
                it.copy(isLoading = false, error = "Club ID not found.")
            }
        }
    }

    private fun fetchClubDetails(clubId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            getClubByIdUseCase(clubId)
                .onSuccess { clubDetail ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            club = clubDetail,
                            error = null
                        )
                    }
                }
                .onFailure { throwable ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = throwable.message ?: "An unknown error occurred."
                        )
                    }
                }
        }
    }
}