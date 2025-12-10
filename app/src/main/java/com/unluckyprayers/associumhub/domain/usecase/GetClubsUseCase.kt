package com.unluckyprayers.associumhub.domain.usecase

import androidx.paging.PagingData
import com.unluckyprayers.associumhub.domain.model.club.ClubItem
import com.unluckyprayers.associumhub.domain.repository.ClubRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetClubsUseCase @Inject constructor(
    private val repository: ClubRepository
) {
    operator fun invoke(): Flow<PagingData<ClubItem>> {
        return repository.getClubsPaging()
    }
}
