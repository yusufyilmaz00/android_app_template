package com.unluckyprayers.associumhub.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sectors")
data class SectorEntity(
    @PrimaryKey
    val id: Int,
    val name: String
)