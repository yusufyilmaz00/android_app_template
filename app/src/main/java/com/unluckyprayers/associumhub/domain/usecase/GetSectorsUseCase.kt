package com.unluckyprayers.associumhub.domain.usecase

import com.unluckyprayers.associumhub.data.local.model.SectorModel
import com.unluckyprayers.associumhub.domain.repository.ClubRepository
import javax.inject.Inject

class GetSectorsUseCase @Inject constructor(
    private val repository: ClubRepository
) {
    suspend operator fun invoke(): Result<List<SectorModel>> {
        return repository.getAllSectors()
    }
}