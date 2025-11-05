package com.unluckyprayers.associumhub.ui.screen.settings

data class Language(val code: String, val name: String)

// Ayarlar ekranının durumunu temsil eden sınıf
data class SettingState(
    val supportedLanguages: List<Language> = emptyList(),
    val currentLanguageCode: String = "en"
)
