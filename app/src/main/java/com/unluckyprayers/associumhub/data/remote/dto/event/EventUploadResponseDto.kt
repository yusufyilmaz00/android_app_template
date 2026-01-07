package com.unluckyprayers.associumhub.data.remote.dto.event

import com.google.gson.annotations.SerializedName

data class EventUploadResponseDto(
    @SerializedName("success")
    val success: Boolean? = null,
    @SerializedName("poster_url")
    val posterUrl: String? = null
)

data class EventUploadErrorDto(
    @SerializedName("error")
    val error: String
)
