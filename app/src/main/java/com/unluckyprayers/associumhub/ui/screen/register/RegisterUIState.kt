package com.unluckyprayers.associumhub.ui.screen.register

import com.unluckyprayers.associumhub.data.local.model.UserState

data class RegisterUIState(
    val email: String = "",
    val password: String = "",
    val authState: UserState = UserState.Idle
)

