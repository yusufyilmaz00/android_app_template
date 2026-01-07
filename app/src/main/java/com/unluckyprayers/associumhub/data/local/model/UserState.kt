package com.unluckyprayers.associumhub.data.local.model

sealed class UserState {
    object Idle : UserState()
    object Loading : UserState()
    data class Success(
        val message: String,
        val role: String = "standard_user",
        val clubId: String? = null
    ) : UserState()
    data class Error(val message: String) : UserState()
}