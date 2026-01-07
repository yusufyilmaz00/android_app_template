package com.unluckyprayers.associumhub.data.remote.mapper

import com.unluckyprayers.associumhub.data.remote.dto.search.sector.SectorDto
import com.unluckyprayers.associumhub.domain.model.SectorUiModel

fun SectorDto.toDomain(): SectorUiModel {
    return SectorUiModel(
        id = this.id,
        name = this.name,
        isSelected = false
    )
}