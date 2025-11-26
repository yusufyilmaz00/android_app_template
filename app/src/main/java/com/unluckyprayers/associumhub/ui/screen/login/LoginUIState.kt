package com.unluckyprayers.associumhub.ui.screen.login

import com.unluckyprayers.associumhub.data.local.model.UserState

data class LoginUIState(
    val email: String = "",
    val password: String = "",
    val authState: UserState = UserState.Idle // Başlangıçta bekleme durumu
)