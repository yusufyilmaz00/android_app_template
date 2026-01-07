package com.unluckyprayers.associumhub.ui.screen.event

import androidx.lifecycle.ViewModel
import com.unluckyprayers.associumhub.domain.model.event.EventUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EventViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(EventUiState())
    val uiState: StateFlow<EventUiState> = _uiState.asStateFlow()

    init {
        loadEvents()
    }

    private fun loadEvents() {
        _uiState.update { it.copy(isLoading = true) }

        // Mock Data (Gerçekte burası API çağrısı veya Veritabanı sorgusu olur)
        val mockEvents = listOf(
            EventUiModel("1", "Tech Innovators Summit", "Robotics Club", "Oct 24 • 7:00 PM", ""),
            EventUiModel("2", "Neon Night Market", "City Culture Group", "Nov 02 • 6:00 PM", ""),
            EventUiModel("3", "Design Systems Workshop", "UX/UI Design Hub", "Nov 15 • 10:00 AM", ""),
            EventUiModel("4", "Startup Pitch Night", "Venture Capital Network", "Nov 20 • 5:30 PM", ""),
            EventUiModel("5", "Creative Coding Jam", "Art & Tech Collective", "Dec 05 • 2:00 PM", ""),
            EventUiModel("6", "Tech Innovators Summit", "Robotics Club", "Oct 24 • 7:00 PM", ""),
            EventUiModel("7", "Neon Night Market", "City Culture Group", "Nov 02 • 6:00 PM", ""),
            EventUiModel("8", "Design Systems Workshop", "UX/UI Design Hub", "Nov 15 • 10:00 AM", ""),
            EventUiModel("9", "Startup Pitch Night", "Venture Capital Network", "Nov 20 • 5:30 PM", ""),
            EventUiModel("10", "Creative Coding Jam", "Art & Tech Collective", "Dec 05 • 2:00 PM", "")
        )

        _uiState.update {
            it.copy(
                isLoading = false,
                events = mockEvents
            )
        }
    }
}