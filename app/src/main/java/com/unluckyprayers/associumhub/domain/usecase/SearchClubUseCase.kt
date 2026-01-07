package com.unluckyprayers.associumhub.domain.usecase

import com.unluckyprayers.associumhub.domain.model.club.SearchClubUiModel
import com.unluckyprayers.associumhub.domain.repository.ClubRepository
import javax.inject.Inject

class SearchClubsUseCase @Inject constructor(
    private val repository: ClubRepository
) {
    suspend operator fun invoke(
        query: String,
        sectorIds: List<Int>,
        page: Int = 1
    ): Result<List<SearchClubUiModel>> {
        // İleride arama yapmadan önce validasyon gerekirse buraya yazarız.
        // Örn: if (query.length < 3) return Result.success(emptyList())

        return repository.searchClubs(query, sectorIds, page)
    }
}