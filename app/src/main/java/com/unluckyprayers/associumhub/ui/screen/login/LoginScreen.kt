package com.unluckyprayers.associumhub.ui.screen.login

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unluckyprayers.associumhub.data.local.model.UserState

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: (role: String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(uiState.authState) {
        when (val state = uiState.authState) {
            is UserState.Success -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                onLoginSuccess(state.role) // Navigasyon için rol bilgisi ile callback'i çağır
            }
            is UserState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
            }
            else -> { /* Idle veya Loading durumunda bir şey yapma */ }
        }
    }

    LoginUI(
        modifier = modifier,
        state = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onLoginClicked = {
            viewModel.onLoginClicked(context)
        },
        onGoogleSignInClicked = {
            Toast.makeText(context, "Google Sign-In not implemented yet.", Toast.LENGTH_SHORT).show()
        },
        onSignUpClicked = onNavigateToRegister // Navigasyon için callback'i çağır
    )
}