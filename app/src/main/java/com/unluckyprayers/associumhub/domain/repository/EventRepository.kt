package com.unluckyprayers.associumhub.domain.repository

import android.net.Uri
import com.unluckyprayers.associumhub.domain.model.event.CreateEventParams
import com.unluckyprayers.associumhub.domain.model.event.EventUploadResult

interface EventRepository {
    suspend fun uploadEventPoster(imageUri: Uri): Result<EventUploadResult>
    suspend fun createEvent(params: CreateEventParams): Result<Unit>
}
