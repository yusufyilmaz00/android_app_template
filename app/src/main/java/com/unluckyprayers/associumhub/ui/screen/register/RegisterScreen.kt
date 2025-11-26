package com.unluckyprayers.associumhub.ui.screen.register

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
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = hiltViewModel(),
    onNavigateToLogin: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(uiState.authState) {
        when (val state = uiState.authState) {
            is UserState.Success -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                onRegisterSuccess()
            }

            is UserState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
            }

            else -> {
                // Idle veya Loading durumunda bir şey yapma
            }
        }
    }

    // RegisterUI'ı çağırırken her şeyi tek bir state objesinden ve ViewModel'den veriyoruz.
    RegisterUI(
        modifier = modifier,
        state = uiState, // Hem email/password hem de authState'i içerir
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onSignUpClicked = {
            viewModel.onSignUpClicked(context)
        },
        onGoogleSignInClicked = {
            Toast.makeText(context, "Google Sign-In not implemented yet.", Toast.LENGTH_SHORT)
                .show()
        },
        onLoginClicked = onNavigateToLogin
    )
}

