package com.unluckyprayers.associumhub.data.remote.dto.search

import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    val success: Boolean,
    val data: List<SearchClubDto>
)