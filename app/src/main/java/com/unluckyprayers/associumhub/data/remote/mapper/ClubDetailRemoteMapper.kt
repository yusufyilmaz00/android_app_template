package com.unluckyprayers.associumhub.data.remote.mapper

import com.unluckyprayers.associumhub.data.remote.dto.club.ClubDetailResponseDto
import com.unluckyprayers.associumhub.domain.model.club.Club

fun ClubDetailResponseDto.toDomainModel(): Club {
    return Club(
        id = this.id,
        name = this.name,
        logoUrl = this.logoUrl,
        coverUrl = this.coverUrl,
        shortDescription = this.shortDescription,
        aboutText = this.aboutText,
        sectors = this.sectors,
        foundedYear = this.foundedYear,
        memberCount = this.memberCount,
        email = this.email
    )
}
