package com.unluckyprayers.associumhub.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.unluckyprayers.associumhub.data.paging.ClubsPagingSource
import com.unluckyprayers.associumhub.data.remote.mapper.toDomainModel
import com.unluckyprayers.associumhub.data.remote.service.ApiService
import com.unluckyprayers.associumhub.domain.model.club.Club
import com.unluckyprayers.associumhub.domain.model.club.ClubItem
import com.unluckyprayers.associumhub.domain.repository.ClubRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ClubRepositoryImpl @Inject constructor(
    private val api: ApiService
) : ClubRepository {

    override fun getClubsPaging(): Flow<PagingData<ClubItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ClubsPagingSource(api) }
        ).flow
    }

    override suspend fun getClubById(clubId: Int): Result<Club> {
        return try {
            val response = api.getClubById(clubId)
            Result.success(response.toDomainModel())
        } catch (e: Exception) {
            // Hata durumunda, hatayı yakalayıp Result.failure ile geri döndürüyoruz.
            // Bu, ViewModel'de hata yönetimini kolaylaştırır.
            Result.failure(e)
        }
    }
}
