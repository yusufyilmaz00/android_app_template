package com.unluckyprayers.associumhub.domain.usecase

import com.unluckyprayers.associumhub.domain.model.club.Club
import com.unluckyprayers.associumhub.domain.repository.ClubRepository
import javax.inject.Inject

class GetClubByIdUseCase @Inject constructor(
    private val clubRepository: ClubRepository
) {
    suspend operator fun invoke(clubId: Int): Result<Club> {
        return clubRepository.getClubById(clubId)
    }
}