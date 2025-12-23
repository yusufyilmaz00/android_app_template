package com.unluckyprayers.associumhub.ui.screen.profile.moderatorprofile

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun ModeratorProfileScreen(
    navController: NavController
) {
    // UI dosyasını çağırıyoruz ve aksiyonları bağlıyoruz
    ModeratorProfileUI(
        onBackClick = {
            // Geri butonuna basılınca önceki sayfaya dön
            navController.popBackStack()
        },
        onMoreOptionsClick = {
            // Şimdilik boş, ileride buraya BottomSheet veya Dialog açtırabilirsin
        }
    )
}