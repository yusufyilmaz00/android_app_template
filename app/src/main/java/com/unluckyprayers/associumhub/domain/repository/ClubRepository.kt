package com.unluckyprayers.associumhub.domain.repository

import androidx.paging.PagingData
import com.unluckyprayers.associumhub.domain.model.club.Club
import kotlinx.coroutines.flow.Flow

interface ClubRepository {
    fun getClubsPaging(): Flow<PagingData<Club>>
}