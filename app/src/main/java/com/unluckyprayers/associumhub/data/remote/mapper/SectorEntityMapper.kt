package com.unluckyprayers.associumhub.data.remote.mapper

import com.unluckyprayers.associumhub.data.local.entity.SectorEntity
import com.unluckyprayers.associumhub.data.local.model.SectorModel
import com.unluckyprayers.associumhub.data.remote.dto.search.sector.SectorDto
import com.unluckyprayers.associumhub.domain.model.SectorUiModel

fun SectorDto.toEntity(): SectorEntity {
    return SectorEntity(
        id = this.id,
        name = this.name
    )
}

fun SectorEntity.toDomain(): SectorModel {
    return SectorModel(
        id = this.id,
        name = this.name
    )
}