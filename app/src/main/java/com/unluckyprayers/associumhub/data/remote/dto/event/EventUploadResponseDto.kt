package com.unluckyprayers.associumhub.data.remote.dto.event

import com.google.gson.annotations.SerializedName

data class EventUploadResponseDto(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("path")
    val path: String? = null,
    @SerializedName("url")
    val url: String? = null
)

data class EventUploadErrorDto(
    @SerializedName("error")
    val error: String
)
