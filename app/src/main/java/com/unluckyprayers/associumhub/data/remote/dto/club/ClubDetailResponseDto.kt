package com.unluckyprayers.associumhub.data.remote.dto.club


import com.google.gson.annotations.SerializedName

/**
 * API'den gelen tek bir kulüp detay verisini karşılayan DTO.
 * @SerializedName annotation'ı, JSON'daki snake_case isimlendirmeyi
 * Kotlin'deki camelCase isimlendirmeye çevirmek için kullanılır.
 */
data class ClubDetailResponseDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("logo_url")
    val logoUrl: String?,

    @SerializedName("cover_url")
    val coverUrl: String?,

    @SerializedName("short_description")
    val shortDescription: String?,

    @SerializedName("about_text")
    val aboutText: String?,

    @SerializedName("sectors")
    val sectors: List<String>,

    @SerializedName("founded_year")
    val foundedYear: Int?,

    @SerializedName("member_count")
    val memberCount: Int?,

    @SerializedName("email")
    val email: String?,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String
)

/**
 * DTO'yu, UI katmanında kullanılacak olan temiz Domain Modeli'ne dönüştürür.
 * Bu, data katmanının detaylarının UI katmanına sızmasını engeller.
 */
