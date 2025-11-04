package com.unluckyprayers.associumhub.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "system_messages")
data class SystemMessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val messageText: String
)