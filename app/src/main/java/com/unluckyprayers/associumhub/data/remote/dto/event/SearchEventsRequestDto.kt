package com.unluckyprayers.associumhub.data.remote.dto.event

import com.google.gson.annotations.SerializedName

data class SearchEventsRequestDto(
    @SerializedName("queryText")
    val queryText: String,
    @SerializedName("page")
    val page: Int,
    @SerializedName("limit")
    val limit: Int
)
