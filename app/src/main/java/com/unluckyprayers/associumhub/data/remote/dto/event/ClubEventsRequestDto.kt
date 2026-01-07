package com.unluckyprayers.associumhub.data.remote.dto.event

import com.google.gson.annotations.SerializedName

data class ClubEventsRequestDto(
    @SerializedName("clubId")
    val clubId: String
)
