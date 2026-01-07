package com.unluckyprayers.associumhub.data.remote.service


import com.unluckyprayers.associumhub.data.remote.dto.club.ClubDetailResponseDto
import com.unluckyprayers.associumhub.data.remote.dto.club.ClubsApiResponseDto
import com.unluckyprayers.associumhub.data.remote.dto.event.ClubEventsRequestDto
import com.unluckyprayers.associumhub.data.remote.dto.event.ClubEventsResponseDto
import com.unluckyprayers.associumhub.data.remote.dto.event.CreateEventRequestDto
import com.unluckyprayers.associumhub.data.remote.dto.event.CreateEventResponseDto
import com.unluckyprayers.associumhub.data.remote.dto.event.EventUploadResponseDto
import com.unluckyprayers.associumhub.data.remote.dto.search.SearchRequest
import com.unluckyprayers.associumhub.data.remote.dto.search.SearchResponse
import com.unluckyprayers.associumhub.data.remote.dto.search.sector.AllSectorsResponse
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("v1/explore-clubs")
    suspend fun getClubs(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 20
    ): ClubsApiResponseDto

    @GET("v1/club/{clubId}")
    suspend fun getClubById(
        @Path("clubId") clubId: Int
    ): ClubDetailResponseDto

    @POST("v1/search-clubs")
    suspend fun searchClubs(
        @Body request: SearchRequest,
    ): SearchResponse

    @GET("v1/club-sectors")
    suspend fun getAllSectors(
    ): AllSectorsResponse

    @Multipart
    @POST("v1/event-upload-poster")
    suspend fun uploadEventPoster(
        @Part file: MultipartBody.Part
    ): EventUploadResponseDto

    @POST("v1/event-create")
    suspend fun createEvent(
        @Body request: CreateEventRequestDto
    ): CreateEventResponseDto

    @POST("v1/club-events")
    suspend fun getClubEvents(
        @Body request: ClubEventsRequestDto
    ): ClubEventsResponseDto
}