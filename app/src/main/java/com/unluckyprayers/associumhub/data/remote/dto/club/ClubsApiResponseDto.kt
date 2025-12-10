package com.unluckyprayers.associumhub.data.remote.dto.club


data class ClubsApiResponseDto(
    val meta: MetaDto,
    val results: List<ClubItemResponseDto>
)
