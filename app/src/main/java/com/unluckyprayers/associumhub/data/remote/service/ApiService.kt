package com.unluckyprayers.associumhub.data.remote.service


import com.unluckyprayers.associumhub.data.remote.dto.club.ClubsApiResponseDto
import retrofit2.http.GET
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("v1/explore-clubs")
    suspend fun getClubs(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 20
    ): ClubsApiResponseDto
}