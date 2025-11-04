package com.unluckyprayers.associumhub.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.unluckyprayers.associumhub.data.local.model.SystemMessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SystemMessageDao {

    // Son eklenen mesajı canlı olarak Flow ile dinle.
    // Veri değiştiğinde UI otomatik güncellenecek.
    @Query("SELECT * FROM system_messages ORDER BY id DESC LIMIT 1")
    fun getLatestMessage(): Flow<SystemMessageEntity?> // Veri olmayabilir diye nullable (?)

    // Eğer aynı veri varsa üzerine yaz yoksa ekle
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertMessage(message: SystemMessageEntity)
}