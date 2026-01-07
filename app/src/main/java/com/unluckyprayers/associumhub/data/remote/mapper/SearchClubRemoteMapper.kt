package com.unluckyprayers.associumhub.data.remote.mapper

import com.unluckyprayers.associumhub.data.remote.dto.search.SearchClubDto
import com.unluckyprayers.associumhub.domain.model.club.ClubItem
import com.unluckyprayers.associumhub.domain.model.club.SearchClubUiModel


fun SearchClubDto.toDomain(): SearchClubUiModel {
    val clubItem = ClubItem(
        id = id,
        name = name ?: "Unknown", // Null safety
        foundedYear = foundedYear ?: 0,
        logoUrl = logoUrl ?: ""
    )
    
    return SearchClubUiModel(
        club = clubItem,
        sectorIds = sectorIds ?: emptyList()
    )
}