package com.unluckyprayers.associumhub.domain.usecase

import com.unluckyprayers.associumhub.domain.model.event.EventUiModel
import com.unluckyprayers.associumhub.domain.repository.EventRepository
import javax.inject.Inject

class SearchEventsUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(queryText: String, page: Int, limit: Int): Result<Pair<List<EventUiModel>, Boolean>> {
        return eventRepository.searchEvents(queryText, page, limit)
    }
}
