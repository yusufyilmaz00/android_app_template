package com.unluckyprayers.associumhub.ui.screen.search

import android.util.Log
import com.unluckyprayers.associumhub.domain.usecase.GetSectorsUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.query
import com.unluckyprayers.associumhub.domain.model.SectorUiModel
import com.unluckyprayers.associumhub.domain.usecase.SearchClubsUseCase
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
class SearchClubViewModel @Inject constructor(
    private val getSectorsUseCase: GetSectorsUseCase,
    private val searchClubsUseCase: SearchClubsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchClubUiState())
    val uiState: StateFlow<SearchClubUiState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    init {
        loadSectors()
    }

    private fun loadSectors() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val result = getSectorsUseCase()
            Log.d("SearchClubViewModel", "loadSectors: $result")

            result.onSuccess { sectors ->

                val uiSectors = sectors.map { sector ->
                    SectorUiModel(
                        id = sector.id,
                        name = sector.name,
                        isSelected = false
                    )
                }

                _uiState.update {
                    it.copy(
                        sectors = uiSectors,
                        isLoading = false
                    )
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = error.message
                    )
                }
            }
        }
    }

    /**
     * Kullanıcı Arama Çubuğuna yazı yazdığında çalışır.
     * 500ms gecikme (debounce) uygular.
     */
    fun onSearchQueryChange(newQuery: String) {
        _uiState.update { it.copy(searchQuery = newQuery) }

        // Eğer önceki bir arama emri varsa iptal et (Henüz yazmaya devam ediyor)
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            delay(500L) // 500ms bekle
            performSearch() // Süre dolunca aramayı yap
        }
    }

    /**
     * Kullanıcı Sektör Çipine tıkladığında çalışır.
     * Bekleme yapmaz, anında arama yapar.
     */
    fun onSectorToggle(sectorId: Int) {
        _uiState.update { currentState ->
            val updatedSectors = currentState.sectors.map { sector ->
                if (sector.id == sectorId) {
                    sector.copy(isSelected = !sector.isSelected)
                } else {
                    sector
                }
            }
            currentState.copy(sectors = updatedSectors)
        }
        // Filtre değişimi kritik olduğu için beklemeden ara
        searchJob?.cancel() // Varsa bekleyen text aramasını iptal et, hemen yenisini başlat
        searchJob = viewModelScope.launch {
            performSearch()
        }
    }

    /**
     * Temizle butonuna basıldığında
     */
    fun onClearSearch() {
        _uiState.update { it.copy(searchQuery = "") }
        // İster tüm listeyi getir, ister boşalt. Genelde boş query tümünü getirir.
        searchJob?.cancel()
        searchJob = viewModelScope.launch { performSearch() }
    }

    /**
     * ASIL ARAMA MANTIĞI
     */
    private suspend fun performSearch() {
        val currentState = _uiState.value
        val query = currentState.searchQuery.trim()

        // Seçili sektörlerin ID'lerini topla
        val selectedIds = currentState.sectors
            .filter { it.isSelected }
            .map { it.id }


        if (currentState.searchQuery.length <2 && currentState.searchQuery.isBlank() && selectedIds.isEmpty()) {
            _uiState.update { it.copy(searchResults = emptyList(),isLoading = false) }
            return
        }


        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        val effectiveQuery = if (currentState.searchQuery.length < 2) "" else currentState.searchQuery

        val result = searchClubsUseCase(
            query = effectiveQuery,
            sectorIds = selectedIds,
            page = 1 // Şimdilik sayfa 1, ilerde Pagination ekleriz
        )

        result.onSuccess { clubs ->
            _uiState.update {
                it.copy(
                    searchResults = clubs,
                    isLoading = false
                )
            }
        }.onFailure { error ->
            _uiState.update {
                it.copy(
                    isLoading = false,
                    errorMessage = error.message ?: "Arama sırasında hata oluştu"
                )
            }
        }
    }

    fun onClearFilters() {
        _uiState.update { currentState ->
            val clearedSectors = currentState.sectors.map { it.copy(isSelected = false) }
            currentState.copy(sectors = clearedSectors)
        }
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            performSearch()
        }
    }
}