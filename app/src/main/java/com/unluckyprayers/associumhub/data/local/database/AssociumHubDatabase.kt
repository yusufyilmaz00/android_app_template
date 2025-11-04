package com.unluckyprayers.associumhub.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.unluckyprayers.associumhub.data.local.dao.SystemMessageDao
import com.unluckyprayers.associumhub.data.local.model.SystemMessageEntity

@Database(entities = [SystemMessageEntity::class], version = 1)
abstract class AssociumHubDatabase : RoomDatabase() {

    abstract fun systemMessageDao(): SystemMessageDao

}