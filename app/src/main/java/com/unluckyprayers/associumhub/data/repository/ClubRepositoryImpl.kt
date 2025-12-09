package com.unluckyprayers.associumhub.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.unluckyprayers.associumhub.data.paging.ClubsPagingSource
import com.unluckyprayers.associumhub.data.remote.service.ApiService
import com.unluckyprayers.associumhub.domain.model.club.Club
import com.unluckyprayers.associumhub.domain.repository.ClubRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ClubRepositoryImpl @Inject constructor(
    private val api: ApiService
) : ClubRepository {

    override fun getClubsPaging(): Flow<PagingData<Club>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ClubsPagingSource(api) }
        ).flow
    }
}
