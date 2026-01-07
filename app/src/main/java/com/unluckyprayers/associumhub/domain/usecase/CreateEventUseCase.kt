package com.unluckyprayers.associumhub.domain.usecase

import com.unluckyprayers.associumhub.domain.model.event.CreateEventParams
import com.unluckyprayers.associumhub.domain.repository.EventRepository
import javax.inject.Inject

class CreateEventUseCase @Inject constructor(
    private val repository: EventRepository
) {
    suspend operator fun invoke(params: CreateEventParams): Result<Unit> {
        return repository.createEvent(params)
    }
}
