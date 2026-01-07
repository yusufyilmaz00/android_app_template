package com.unluckyprayers.associumhub.data.remote.dto.search.sector

import kotlinx.serialization.Serializable

@Serializable
data class AllSectorsResponse(
    val success: Boolean,
    val data: List<SectorDto>
)