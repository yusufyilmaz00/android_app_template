package com.unluckyprayers.associumhub.data.remote.dto.event

import com.google.gson.annotations.SerializedName

// Success response (status 201): { message: "Event created", data: [...] }
// Error response (status 400/401/403/500): { error: "..." }
data class CreateEventResponseDto(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("data")
    val data: List<EventDataDto>? = null,
    @SerializedName("error")
    val error: String? = null
)

data class EventDataDto(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("event_date")
    val eventDate: String? = null,
    @SerializedName("event_time")
    val eventTime: String? = null,
    @SerializedName("location")
    val location: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("image_url")
    val imageUrl: String? = null,
    @SerializedName("club_id")
    val clubId: String? = null
)
