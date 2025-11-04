package com.unluckyprayers.associumhub.data.repository

import com.unluckyprayers.associumhub.data.local.dao.SystemMessageDao
import com.unluckyprayers.associumhub.data.local.model.SystemMessageEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SystemMessageRepository @Inject constructor(
    private val systemMessageDao: SystemMessageDao
) {
    fun getLatestMessage(): Flow<SystemMessageEntity?> {
        return systemMessageDao.getLatestMessage()
    }

    suspend fun initializeSystemMessages() {

        val existingMessage = systemMessageDao.getLatestMessage().first()

        if (existingMessage == null) {
            val initialMessage = SystemMessageEntity(messageText = "Çok yakında hizmetinizdeyiz.\n\nAssocium Hub Ekibi")
            systemMessageDao.insertMessage(initialMessage)
        }
    }
}