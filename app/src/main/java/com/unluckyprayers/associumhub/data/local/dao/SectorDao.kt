package com.unluckyprayers.associumhub.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.unluckyprayers.associumhub.data.local.entity.SectorEntity

@Dao
interface SectorDao {
    @Query("SELECT * FROM sectors ORDER BY name ASC")
    suspend fun getAllSectors(): List<SectorEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSectors(sectors: List<SectorEntity>)

    @Query("DELETE FROM sectors")
    suspend fun clearSectors()
}