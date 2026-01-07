package com.unluckyprayers.associumhub.domain.usecase

import android.net.Uri
import com.unluckyprayers.associumhub.domain.model.event.EventUploadResult
import com.unluckyprayers.associumhub.domain.repository.EventRepository
import javax.inject.Inject

class UploadEventPosterUseCase @Inject constructor(
    private val repository: EventRepository
) {
    suspend operator fun invoke(imageUri: Uri): Result<EventUploadResult> {
        return repository.uploadEventPoster(imageUri)
    }
}
