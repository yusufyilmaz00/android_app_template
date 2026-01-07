package com.unluckyprayers.associumhub.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.gson.Gson
import com.unluckyprayers.associumhub.data.remote.dto.event.ClubEventsRequestDto
import com.unluckyprayers.associumhub.data.remote.dto.event.CreateEventRequestDto
import com.unluckyprayers.associumhub.data.remote.dto.event.CreateEventResponseDto
import com.unluckyprayers.associumhub.data.remote.dto.event.SearchEventsRequestDto
import com.unluckyprayers.associumhub.data.remote.dto.event.SearchEventsResponseDto
import com.unluckyprayers.associumhub.data.remote.mapper.toDomain
import com.unluckyprayers.associumhub.data.remote.service.ApiService
import com.unluckyprayers.associumhub.domain.model.event.CreateEventParams
import com.unluckyprayers.associumhub.domain.model.event.EventUiModel
import com.unluckyprayers.associumhub.domain.model.event.EventUploadResult
import com.unluckyprayers.associumhub.domain.repository.EventRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val api: ApiService,
    @ApplicationContext private val context: Context,
    private val gson: Gson
) : EventRepository {

    override suspend fun uploadEventPoster(imageUri: Uri): Result<EventUploadResult> {
        return try {
            // Uri'den geçici dosya oluştur
            val file = getFileFromUri(imageUri)
                ?: return Result.failure(Exception("Dosya okunamadı"))

            // MultipartBody.Part oluştur
            val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
            val multipartBody = MultipartBody.Part.createFormData("file", file.name, requestBody)

            // API çağrısı yap
            val response = api.uploadEventPoster(multipartBody)
            
            Log.d("EventRepository", "Upload response: success=${response.success}, posterUrl=${response.posterUrl}")

            // Geçici dosyayı sil
            file.delete()

            // Domain modele dönüştür
            val uploadResult = EventUploadResult(
                message = null,
                path = null,
                url = response.posterUrl
            )
            Log.d("EventRepository", "EventUploadResult created: url=${uploadResult.url}")
            Result.success(uploadResult)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun createEvent(params: CreateEventParams): Result<Unit> {
        return try {
            val request = CreateEventRequestDto(
                title = params.title,
                date = params.date,
                time = params.time,
                location = params.location,
                description = params.description,
                imageUrl = params.imageUrl,
                clubId = params.clubId
            )

            // Request body'yi logla
            Log.d("CreateEvent", "=== CREATE EVENT REQUEST ===")
            Log.d("CreateEvent", "Title: ${request.title}")
            Log.d("CreateEvent", "Date: ${request.date}")
            Log.d("CreateEvent", "Time: ${request.time}")
            Log.d("CreateEvent", "Location: ${request.location}")
            Log.d("CreateEvent", "Description: ${request.description}")
            Log.d("CreateEvent", "ImageUrl: ${request.imageUrl}")
            Log.d("CreateEvent", "ClubId: ${request.clubId}")
            Log.d("CreateEvent", "============================")

            val response = api.createEvent(request)

            // Response'u logla
            Log.d("CreateEvent", "=== CREATE EVENT RESPONSE (Success) ===")
            Log.d("CreateEvent", "Message: ${response.message}")
            Log.d("CreateEvent", "Data: ${response.data}")
            Log.d("CreateEvent", "=======================================")

            // Success: message ve data varsa başarılı
            if (response.message != null && response.data != null) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.error ?: "Etkinlik oluşturulamadı"))
            }
        } catch (e: HttpException) {
            // HTTP error (403, 401, 500, etc.)
            val errorBody = e.response()?.errorBody()?.string()
            Log.e("CreateEvent", "HTTP Exception (${e.code()}): $errorBody")
            
            // Error response'u parse et
            val errorMessage = try {
                val errorResponse = gson.fromJson(errorBody, CreateEventResponseDto::class.java)
                errorResponse.error ?: "HTTP ${e.code()}: ${e.message()}"
            } catch (parseException: Exception) {
                "HTTP ${e.code()}: ${errorBody ?: e.message()}"
            }
            
            Result.failure(Exception(errorMessage))
        } catch (e: Exception) {
            Log.e("CreateEvent", "Exception: ${e.message}", e)
            e.printStackTrace()
            Result.failure(e)
        }
    }

    private fun getFileFromUri(uri: Uri): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val fileName = "temp_image_${System.currentTimeMillis()}.jpg"
            val tempFile = File(context.cacheDir, fileName)
            
            FileOutputStream(tempFile).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
            inputStream.close()
            
            tempFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getClubEvents(clubId: String): Result<List<EventUiModel>> {
        return try {
            val request = ClubEventsRequestDto(clubId = clubId)
            val response = api.getClubEvents(request)

            if (response.error != null) {
                Result.failure(Exception(response.error))
            } else {
                val events = response.data?.map { it.toDomain() } ?: emptyList()
                Result.success(events)
            }
        } catch (e: Exception) {
            Log.e("EventRepository", "Error fetching club events: ${e.message}", e)
            Result.failure(e)
        }
    }

    override suspend fun searchEvents(queryText: String, page: Int, limit: Int): Result<Pair<List<EventUiModel>, Boolean>> {
        return try {
            val request = SearchEventsRequestDto(
                queryText = queryText,
                page = page,
                limit = limit
            )
            val response = api.searchEvents(request)

            val events = response.data?.map { it.toDomain() } ?: emptyList()
            val hasMore = response.meta?.hasMore ?: false
            
            Result.success(Pair(events, hasMore))
        } catch (e: Exception) {
            Log.e("EventRepository", "Error searching events: ${e.message}", e)
            Result.failure(e)
        }
    }
}
