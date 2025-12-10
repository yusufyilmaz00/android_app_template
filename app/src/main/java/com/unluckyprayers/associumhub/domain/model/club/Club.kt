package com.unluckyprayers.associumhub.domain.model.club

data class Club(
    val id: Int,
    val name: String,
    val logoUrl: String?,
    val coverUrl: String?,
    val shortDescription: String?,
    val aboutText: String?,
    val sectors: List<String>,
    val foundedYear: Int?,
    val memberCount: Int?,
    val email: String?
)