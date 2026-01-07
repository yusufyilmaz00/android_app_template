package com.unluckyprayers.associumhub.data.remote.dto.search.sector

import kotlinx.serialization.Serializable

@Serializable
data class SectorDto(
    val id: Int,
    val name: String
)