package com.unluckyprayers.associumhub.data.remote.dto.search

import android.util.Log
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchClubDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("founded_year") val foundedYear: Int?,
    @SerializedName("logo_url") val logoUrl: String?,
    // Search RPC'si bize sadece ID listesi dönüyor, detay dönmüyor
    @SerializedName("sector_ids") val sectorIds: List<Int>? = null,
    // Search RPC'sine özel bir alan (Pagination için)
    @SerializedName("total_count") val totalCount: Long? = null
){
    init {
        Log.d("SearchClubDto", "ID: $id, Name: $name, FoundedYear: $foundedYear, LogoUrl: $logoUrl")
    }
}