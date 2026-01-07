package com.unluckyprayers.associumhub.data.remote.dto.event

import com.google.gson.annotations.SerializedName

data class ClubEventsResponseDto(
    @SerializedName("data")
    val data: List<ClubEventDto>? = null,
    @SerializedName("error")
    val error: String? = null
)

data class ClubEventDto(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("club_name")
    val clubName: String? = null,
    @SerializedName("event_date")
    val eventDate: String? = null,
    @SerializedName("event_time")
    val eventTime: String? = null,
    @SerializedName("image_url")
    val imageUrl: String? = null,
    @SerializedName("location")
    val location: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("club_id")
    val clubId: Int? = null
)
