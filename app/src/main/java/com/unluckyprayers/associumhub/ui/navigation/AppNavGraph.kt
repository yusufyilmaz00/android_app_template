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
        startDestination = Routes.PAGE1,
        modifier = modifier
    ) {
        composable(Routes.PAGE1)
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
