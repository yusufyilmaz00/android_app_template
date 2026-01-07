package com.unluckyprayers.associumhub.data.remote.dto.event

import com.google.gson.annotations.SerializedName

data class CreateEventRequestDto(
    @SerializedName("title")
    val title: String,
    @SerializedName("date")
    val date: String, // Format: "2024-11-15"
    @SerializedName("time")
    val time: String, // Format: "20:00"
    @SerializedName("location")
    val location: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("clubId")
    val clubId: String
)
