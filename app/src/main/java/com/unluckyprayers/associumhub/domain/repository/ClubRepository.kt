package com.unluckyprayers.associumhub.domain.repository

import androidx.paging.PagingData
import com.unluckyprayers.associumhub.data.local.model.SectorModel
import com.unluckyprayers.associumhub.domain.model.club.Club
import com.unluckyprayers.associumhub.domain.model.club.ClubItem
import com.unluckyprayers.associumhub.domain.model.club.SearchClubUiModel
import kotlinx.coroutines.flow.Flow

interface ClubRepository {
    fun getClubsPaging(): Flow<PagingData<ClubItem>>

    suspend fun getClubById(clubId: Int): Result<Club>

    suspend fun getAllSectors(): Result<List<SectorModel>>

    suspend fun searchClubs(
        query: String,
        sectorIds: List<Int>,
        page: Int = 1
    ): Result<List<SearchClubUiModel>>
}