package com.unluckyprayers.associumhub.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.unluckyprayers.associumhub.data.local.dao.SectorDao
import com.unluckyprayers.associumhub.data.local.model.SectorModel
import com.unluckyprayers.associumhub.data.paging.ClubsPagingSource
import com.unluckyprayers.associumhub.data.remote.dto.search.SearchRequest
import com.unluckyprayers.associumhub.data.remote.mapper.toDomain
import com.unluckyprayers.associumhub.data.remote.mapper.toDomainModel
import com.unluckyprayers.associumhub.data.remote.mapper.toEntity
import com.unluckyprayers.associumhub.data.remote.service.ApiService
import com.unluckyprayers.associumhub.domain.model.SectorUiModel
import com.unluckyprayers.associumhub.domain.model.club.Club
import com.unluckyprayers.associumhub.domain.model.club.ClubItem
import com.unluckyprayers.associumhub.domain.model.club.SearchClubUiModel
import com.unluckyprayers.associumhub.domain.repository.ClubRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ClubRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val sectorDao: SectorDao
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
            Result.failure(e)
        }
    }

    override suspend fun searchClubs(
        query: String,
        sectorIds: List<Int>,
        page: Int
    ): Result<List<SearchClubUiModel>> {
        return try {
            val request = SearchRequest(
                query = query,
                sectors = sectorIds,
                page = page
            )

            val response = api.searchClubs(
                request = request
            )

            if (response.success) {
                val domainData = response.data.map { it.toDomain() }
                Result.success(domainData)
            } else {
                Result.failure(Exception("API Success False Döndü"))
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getAllSectors(): Result<List<SectorModel>> {

        return try {
            // B) Cache boşsa API'ye istek at
            val localSectors = sectorDao.getAllSectors()

            if (localSectors.isNotEmpty()) {
                // Veri varsa direkt döndür
                return Result.success(localSectors.map { it.toDomain() })
            }

            val response = api.getAllSectors()

            if (response.success) {
                val entities = response.data.map { it.toEntity() }
                sectorDao.insertSectors(entities)

                val domainData = entities.map { it.toDomain() }
                Result.success(domainData)
            } else {
                Result.failure(Exception("Sektör listesi alınamadı: success=false"))
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}
