package com.unluckyprayers.associumhub.data.remote.dto.search

import kotlinx.serialization.Serializable

@Serializable
data class SearchRequest(
    val query: String,
    val sectors: List<Int>,
    val page: Int
)