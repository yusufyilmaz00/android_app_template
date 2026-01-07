package com.unluckyprayers.associumhub.domain.viewmodel

import android.content.Context
import androidx.compose.animation.core.copy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unluckyprayers.associumhub.data.local.model.UserState
import com.unluckyprayers.associumhub.data.repository.AuthRepository
import com.unluckyprayers.associumhub.ui.common.AppState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppState())
    val uiState = _uiState.asStateFlow()

    // AuthRepository'den gelen oturum durumunu dışarıya açıyoruz
    val authState: StateFlow<UserState> = authRepository.userState

    /**
     * Uygulama açıldığında kullanıcının oturum durumunu kontrol eder.
     */
    fun checkUserSession(context: Context) {
        viewModelScope.launch {
            // Global yükleme ekranını göster, çünkü oturum kontrolü yapılıyor.
            // Bu, AppNavGraph'taki SPLASH rotasına olan ihtiyacı ortadan kaldırabilir.
            showAppLoading(true)
            authRepository.isUserLoggedIn(context)
            // Kontrol bittikten sonra yükleme ekranını gizle.
            showAppLoading(false)
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }

    /**
     * Uygulama genelindeki tam ekran yükleme animasyonunu gösterir veya gizler.
     * @param show Yükleme ekranı gösterilsin mi?
     */
    fun showAppLoading(show: Boolean) {
        _uiState.update { it.copy(isAppLoading = show) }
    }
}