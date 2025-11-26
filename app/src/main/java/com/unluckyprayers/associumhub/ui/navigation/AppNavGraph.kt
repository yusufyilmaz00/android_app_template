package com.unluckyprayers.associumhub.ui.navigation

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.unluckyprayers.associumhub.ui.screen.home.HomeScreen
import com.unluckyprayers.associumhub.ui.screen.login.LoginScreen
import com.unluckyprayers.associumhub.ui.screen.register.RegisterScreen
import com.unluckyprayers.associumhub.ui.screen.settings.SettingScreen
import com.unluckyprayers.associumhub.ui.screen.settings.SettingsViewModel
import com.unluckyprayers.associumhub.ui.screen.template.DrawerTemplateUI
import com.unluckyprayers.associumhub.ui.screen.template.TemplateUI

@Composable
fun AppNavGraph(
    navController: NavController,
    modifier: Modifier = Modifier,
    onShowAppLoading: (Boolean) -> Unit,
    onChangeLanguage: (String) -> Unit
) {
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
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        // screen pages
        composable(Routes.HOME)
        {
            HomeScreen()
        }

        composable(Routes.PAGE2)
        {
            TemplateUI()
        }

        composable(Routes.PAGE3)
        {
            TemplateUI()
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
