package com.unluckyprayers.associumhub.domain.model.event

data class CreateEventParams(
    val title: String,
    val date: String,      // Format: "2024-11-15"
    val time: String,      // Format: "20:00"
    val location: String,
    val description: String,
    val imageUrl: String,
    val clubId: String
)
