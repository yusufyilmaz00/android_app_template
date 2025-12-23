package com.unluckyprayers.associumhub.ui.screen.profile.userprofile

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.unluckyprayers.associumhub.ui.navigation.Routes


@Composable
fun UserProfileScreen(
    navController: NavController
) {
    UserProfileUI(
        onBackClick = {
            navController.popBackStack()
        },
        onSettingsClick = {
            // Ayarlar sayfasına gitmek istersen:
            navController.navigate(Routes.SETTINGS)
        }
    )
}