package com.unluckyprayers.associumhub.data.remote.service


import com.unluckyprayers.associumhub.data.remote.dto.club.ClubDetailResponseDto
import com.unluckyprayers.associumhub.data.remote.dto.club.ClubsApiResponseDto
import com.unluckyprayers.associumhub.data.remote.dto.search.SearchRequest
import com.unluckyprayers.associumhub.data.remote.dto.search.SearchResponse
import com.unluckyprayers.associumhub.data.remote.dto.search.sector.AllSectorsResponse
import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.POST
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
}