package com.unluckyprayers.associumhub.ui.screen.club


import androidx.compose.runtime.Composable
import com.unluckyprayers.associumhub.domain.model.club.Club

@Composable
fun ClubDetailScreen(
    clubId: Int
) {
    val myClub = Club(
        id = clubId,
        name = "Example Club",
        logoUrl = "https://example.com/logo.png",
        coverUrl = "https://example.com/cover.jpg",
        shortDescription = "This is an example club.",
        aboutText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
        sectors = listOf("Sector 1", "Sector 2"),
        foundedYear = 2023,
        memberCount = 50,
        email = "william.henry.harrison@example-pet-store.com"
    )


    ClubDetailUI(myClub)
}