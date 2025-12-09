package com.unluckyprayers.associumhub.data.remote.mapper

import com.unluckyprayers.associumhub.data.remote.dto.club.ClubResponseDto
import com.unluckyprayers.associumhub.domain.model.club.Club

fun ClubResponseDto.toDomain() = Club(
    id = id,
    name = name,
    logoUrl = logo_url,
    foundedYear = founded_year
)