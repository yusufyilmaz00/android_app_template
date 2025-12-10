package com.unluckyprayers.associumhub.data.remote.mapper

import com.unluckyprayers.associumhub.data.remote.dto.club.ClubItemResponseDto
import com.unluckyprayers.associumhub.domain.model.club.ClubItem

fun ClubItemResponseDto.toDomain() = ClubItem(
    id = id,
    name = name,
    logoUrl = logo_url,
    foundedYear = founded_year
)