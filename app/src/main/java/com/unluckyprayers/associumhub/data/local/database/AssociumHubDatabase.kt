package com.unluckyprayers.associumhub.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.unluckyprayers.associumhub.data.local.dao.SectorDao
import com.unluckyprayers.associumhub.data.local.dao.SystemMessageDao
import com.unluckyprayers.associumhub.data.local.entity.SectorEntity
import com.unluckyprayers.associumhub.data.local.entity.SystemMessageEntity

@Database(entities = [
    SystemMessageEntity::class,
    SectorEntity::class
    ],
    version = 2)
abstract class AssociumHubDatabase : RoomDatabase() {

    abstract fun systemMessageDao(): SystemMessageDao
    abstract fun sectorDao(): SectorDao
}