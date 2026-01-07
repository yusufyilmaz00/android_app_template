package com.unluckyprayers.associumhub.di

import com.unluckyprayers.associumhub.data.local.dao.SectorDao
import com.unluckyprayers.associumhub.data.remote.service.ApiService
import com.unluckyprayers.associumhub.data.repository.ClubRepositoryImpl
import com.unluckyprayers.associumhub.domain.repository.ClubRepository
import com.unluckyprayers.associumhub.domain.usecase.GetClubsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ClubsModule {

    @Provides
    @Singleton
    fun provideClubsApi(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideClubRepository(api: ApiService, sectorDao: SectorDao): ClubRepository =
        ClubRepositoryImpl(api, sectorDao)

    @Provides
    @Singleton
    fun provideGetClubsUseCase(repository: ClubRepository): GetClubsUseCase =
        GetClubsUseCase(repository)
}
