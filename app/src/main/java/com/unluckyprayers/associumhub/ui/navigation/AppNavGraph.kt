package com.unluckyprayers.associumhub.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.unluckyprayers.associumhub.ui.screen.home.HomeScreen
import com.unluckyprayers.associumhub.ui.screen.home.HomeUI
import com.unluckyprayers.associumhub.ui.screen.template.TemplateUI

@Composable
fun AppNavGraph(navController: NavController,
                modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = Routes.PAGE1
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

    }
}
