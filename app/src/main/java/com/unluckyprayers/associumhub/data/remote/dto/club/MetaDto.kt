package com.unluckyprayers.associumhub.data.remote.dto.club

data class MetaDto(
    val page: Int,
    val limit: Int,
    val total: Int,
    val has_next_page: Boolean
)