package com.unluckyprayers.associumhub.ui.screen.userevent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unluckyprayers.associumhub.domain.usecase.SearchEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserEventViewModel @Inject constructor(
    private val searchEventsUseCase: SearchEventsUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(UserEventUiState())
    val uiState: StateFlow<UserEventUiState> = _uiState.asStateFlow()
    
    private var searchJob: Job? = null
    private val LIMIT = 10

    init {
        // İlk yüklemede boş query ile tüm etkinlikleri getir
        loadEvents(queryText = "", page = 0, isInitialLoad = true)
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        
        // Debounce: 500ms bekledikten sonra arama yap
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            loadEvents(queryText = query, page = 0, isInitialLoad = true)
        }
    }

    fun onClearSearch() {
        _uiState.update { it.copy(searchQuery = "") }
        loadEvents(queryText = "", page = 0, isInitialLoad = true)
    }

    fun loadMoreEvents() {
        val currentState = _uiState.value
        if (currentState.isLoadingMore || !currentState.hasMore) {
            return
        }
        
        val nextPage = currentState.currentPage + 1
        loadEvents(queryText = currentState.searchQuery, page = nextPage, isInitialLoad = false)
    }

    private fun loadEvents(queryText: String, page: Int, isInitialLoad: Boolean) {
        viewModelScope.launch {
            if (isInitialLoad) {
                _uiState.update { 
                    it.copy(
                        isLoading = true, 
                        errorMessage = null,
                        events = emptyList(),
                        currentPage = 0,
                        hasMore = true
                    ) 
                }
            } else {
                _uiState.update { it.copy(isLoadingMore = true, errorMessage = null) }
            }

            searchEventsUseCase(queryText.trim(), page, LIMIT)
                .fold(
                    onSuccess = { (events, hasMore) ->
                        _uiState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                isLoadingMore = false,
                                events = if (isInitialLoad) events else currentState.events + events,
                                currentPage = page,
                                hasMore = hasMore,
                                errorMessage = null
                            )
                        }
                    },
                    onFailure = { exception ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isLoadingMore = false,
                                errorMessage = exception.message ?: "Etkinlikler yüklenemedi"
                            )
                        }
                    }
                )
        }
    }
}
