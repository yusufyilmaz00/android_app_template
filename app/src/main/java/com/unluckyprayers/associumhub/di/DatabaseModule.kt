package com.unluckyprayers.associumhub.di

import android.content.Context
import androidx.room.Room
import com.unluckyprayers.associumhub.data.local.dao.SystemMessageDao
import com.unluckyprayers.associumhub.data.local.database.AssociumHubDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAssociumHubDatabase(
        @ApplicationContext context: Context
    ): AssociumHubDatabase {
        return Room.databaseBuilder(
            context,
            AssociumHubDatabase::class.java,
            "associum_hub_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideSystemMessageDao(database: AssociumHubDatabase): SystemMessageDao {
        return database.systemMessageDao()
    }
}