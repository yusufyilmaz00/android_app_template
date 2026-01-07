package com.unluckyprayers.associumhub.ui.navigation

object Routes{
    // login and register
    const val REGISTER = "Register"
    const val LOGIN = "Login"

    // screen pages (standard user)
    const val HOME = "Home"
    const val PAGE2 = "Page 2"
    const val PROFILE = "Profile"

    // moderator only pages
    const val MODERATOR_EVENT = "ModeratorEvent"
    const val CREATE_EVENT = "CreateEvent"
    const val QR_CODE_BASE = "QRCode"
    const val EVENT_ID_ARG = "eventId"
    const val EVENT_TITLE_ARG = "eventTitle"
    const val EVENT_IMAGE_URL_ARG = "eventImageUrl"
    const val QR_CODE = "$QR_CODE_BASE/{$EVENT_ID_ARG}/{$EVENT_TITLE_ARG}/{$EVENT_IMAGE_URL_ARG}"

    // functional screens
    const val CLUB_DETAIL_BASE = "Club"
    const val CLUB_ID_ARG = "ClubId"
    const val CLUB_DETAIL = "$CLUB_DETAIL_BASE/{$CLUB_ID_ARG}"
    // drawer test
    const val DRAWER1= "Drawer 1"
    const val DRAWER2 = "Drawer 2"
    const val DRAWER3 = "Drawer 3"

    // settings
    const val SETTINGS = "Settings"

    val authRoutes = listOf(LOGIN, REGISTER)
    fun isAuthRoute(route: String?): Boolean {
        return route in authRoutes
    }
}