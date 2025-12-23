package com.unluckyprayers.associumhub.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.unluckyprayers.associumhub.data.local.model.UserState
import com.unluckyprayers.associumhub.domain.viewmodel.AppViewModel
import com.unluckyprayers.associumhub.ui.screen.club.ClubDetailScreen
import com.unluckyprayers.associumhub.ui.screen.home.HomeScreen
import com.unluckyprayers.associumhub.ui.screen.login.LoginScreen
import com.unluckyprayers.associumhub.ui.screen.profile.moderatorprofile.ModeratorProfileScreen
import com.unluckyprayers.associumhub.ui.screen.profile.userprofile.UserProfileScreen
import com.unluckyprayers.associumhub.ui.screen.register.RegisterScreen
import com.unluckyprayers.associumhub.ui.screen.settings.SettingScreen
import com.unluckyprayers.associumhub.ui.screen.settings.SettingsViewModel
import com.unluckyprayers.associumhub.ui.screen.template.DrawerTemplateUI
import com.unluckyprayers.associumhub.ui.screen.template.TemplateUI

@Composable
fun AppNavGraph(
    navController: NavController,
    modifier: Modifier = Modifier,
    authState: UserState,
    onShowAppLoading: (Boolean) -> Unit,
    onChangeLanguage: (String) -> Unit
) {
    val startDestination = when (authState) {
        is UserState.Success -> Routes.HOME
        else -> Routes.LOGIN
    }

    NavHost(
        navController = navController as NavHostController,
        startDestination = Routes.REGISTER,
        modifier = modifier
    ) {
        // login and register
        composable(Routes.REGISTER){
            RegisterScreen(
                onNavigateToLogin = {
                    navController.navigate(Routes.LOGIN)
                },
                onRegisterSuccess = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.REGISTER) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(Routes.REGISTER)
                },
                onLoginSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(0)
                    }
                }
            )
        }

        // screen pages
        composable(Routes.HOME)
        {
            HomeScreen(
                onClubClick = { clubId ->
                    navController.navigate("${Routes.CLUB_DETAIL_BASE}/$clubId")
                }
            )
        }

        composable(Routes.PAGE2)
        {
            TemplateUI()
        }

        composable(Routes.PROFILE) {
            // 1. AppViewModel'i Hilt ile alıyoruz (Mevcut AppViewModel'ini kullanıyoruz)
            val appViewModel: AppViewModel = hiltViewModel()

            // 2. AuthState'i dinliyoruz (Repository'den gelen userState)
            val authState by appViewModel.authState.collectAsState()

            // 3. Router Mantığı (Gelen State'e göre yönlendirme)
            when (val state = authState) {
                is UserState.Success -> {
                    // Kullanıcı Rol Kontrolü
                    if (state.role == "moderator") {
                        ModeratorProfileScreen(navController = navController)
                    } else {
                        //UserProfileScreen(navController = navController)
                        ModeratorProfileScreen(navController = navController)
                    }
                }

                is UserState.Loading -> {
                    // Yükleniyor animasyonu
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is UserState.Error -> {
                    // Hata durumunda kullanıcıya bilgi ver veya Login'e yönlendir
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Oturum bilgisi alınamadı: ${state.message}")
                    }
                }

                is UserState.Idle -> {
                    // Eğer veri henüz tetiklenmediyse boş ekran göster
                    // Genelde MainActivity'de checkUserSession çağrıldığı için buraya düşmez.
                    Box(modifier = Modifier.fillMaxSize())
                }
            }
        }

        // functional pages
        composable(
            route = Routes.CLUB_DETAIL,
            arguments = listOf(navArgument(Routes.CLUB_ID_ARG) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val clubId = backStackEntry.arguments?.getInt(Routes.CLUB_ID_ARG) ?: -1
            ClubDetailScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(Routes.DRAWER1)
        {
            DrawerTemplateUI()
        }

        composable(Routes.DRAWER2)
        {
            DrawerTemplateUI()
        }


        composable(Routes.DRAWER3)
        {
            DrawerTemplateUI()
        }

        composable(Routes.SETTINGS)
        {
            val viewModel: SettingsViewModel = hiltViewModel()
            LaunchedEffect(Unit) {
                viewModel.events.collect { event ->
                    when (event) {
                        is SettingsViewModel.SettingsEvent.ChangeLanguage -> {
                            println("DEBUG: ChangeLanguage event'i - code: ${event.code}")
                            onChangeLanguage(event.code)
                        }
                    }
                }
            }


            SettingScreen(
                viewModel = viewModel
            )
        }

    }
}
