package com.unluckyprayers.associumhub.domain.viewmodel

import androidx.lifecycle.ViewModel
import com.unluckyprayers.associumhub.ui.common.AppState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(AppState())
    val uiState = _uiState.asStateFlow()

    /**
     * Uygulama genelindeki tam ekran yükleme animasyonunu gösterir veya gizler.
     * @param show Yükleme ekranı gösterilsin mi?
     */
    fun showAppLoading(show: Boolean) {
        _uiState.update { it.copy(isAppLoading = show) }
    }
}
