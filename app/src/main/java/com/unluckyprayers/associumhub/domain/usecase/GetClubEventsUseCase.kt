package com.unluckyprayers.associumhub.domain.usecase

import com.unluckyprayers.associumhub.domain.model.event.EventUiModel
import com.unluckyprayers.associumhub.domain.repository.EventRepository
import javax.inject.Inject

class GetClubEventsUseCase @Inject constructor(
    private val repository: EventRepository
) {
    suspend operator fun invoke(clubId: String): Result<List<EventUiModel>> {
        return repository.getClubEvents(clubId)
    }
}
