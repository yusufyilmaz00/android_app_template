package com.unluckyprayers.associumhub.data.remote.dto.event

import com.google.gson.annotations.SerializedName

data class SearchEventsResponseDto(
    @SerializedName("data")
    val data: List<SearchEventDto>?,
    @SerializedName("meta")
    val meta: SearchEventsMetaDto?
)

data class SearchEventDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("event_date")
    val eventDate: String,
    @SerializedName("location")
    val location: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("club_id")
    val clubId: Int?
)

data class SearchEventsMetaDto(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("limit")
    val limit: Int?,
    @SerializedName("total_count")
    val totalCount: Int?,
    @SerializedName("has_more")
    val hasMore: Boolean?
)
