package com.unluckyprayers.associumhub.data.repository

import android.content.Context
import com.unluckyprayers.associumhub.data.local.model.UserState
import com.unluckyprayers.associumhub.data.local.pref.SharedPrefManager
import com.unluckyprayers.associumhub.data.remote.service.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor() {

    private val client = SupabaseClient.client
    private val _userState = MutableStateFlow<UserState>(UserState.Idle)
    val userState: StateFlow<UserState> = _userState.asStateFlow()

    fun resetAuthState() {
        _userState.value = UserState.Idle
    }

    suspend fun signUp(context: Context, userEmail: String, userPassword: String) {
        _userState.value = UserState.Loading
        try {
            client.auth.signUpWith(Email) {
                email = userEmail
                password = userPassword
            }
            saveToken(context)
            _userState.value = UserState.Success("Registered user successfully!")
        } catch (e: Exception) {
            _userState.value = UserState.Error(e.message ?: "Unknown sign-up error")
        }
    }

    suspend fun login(context: Context, userEmail: String, userPassword: String) {
        _userState.value = UserState.Loading
        try {
            client.auth.signInWith(Email) {
                email = userEmail
                password = userPassword
            }
            saveToken(context)
            _userState.value = UserState.Success("Logged in user successfully!")
        } catch (e: Exception) {
            _userState.value = UserState.Error(e.message ?: "Unknown login error")
        }
    }

    suspend fun logout() {
        try {
            client.auth.signOut()
            _userState.value = UserState.Success("Logged out successfully!")
        } catch (e: Exception) {
            _userState.value = UserState.Error(e.message ?: "Unknown logout error")
        }
    }

    private suspend fun saveToken(context: Context) {
        val accessToken = client.auth.currentAccessTokenOrNull()
        if (accessToken != null) {
            val sharedPref = SharedPrefManager(context)
            sharedPref.saveString("accessToken", accessToken)
        }
    }

    private fun getToken(context: Context): String? {
        val sharedPref = SharedPrefManager(context)
        return sharedPref.getStringData("accessToken")
    }

    suspend fun isUserLoggedIn(context: Context) {
        try {
            val token = getToken(context)
            if (token.isNullOrEmpty()) {
                _userState.value = UserState.Error("User is not logged in!")
            } else {
                client.auth.retrieveUser(token)
                client.auth.refreshCurrentSession()
                saveToken(context)
                _userState.value = UserState.Success("User is already logged in!")
            }
        } catch (e: Exception) {
            _userState.value = UserState.Error(e.message ?: "Unknown error while checking user status")
        }
    }
}
