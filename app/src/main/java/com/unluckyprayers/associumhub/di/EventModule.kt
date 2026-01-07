package com.unluckyprayers.associumhub.di

import android.content.Context
import com.unluckyprayers.associumhub.data.remote.service.ApiService
import com.unluckyprayers.associumhub.data.repository.EventRepositoryImpl
import com.unluckyprayers.associumhub.domain.repository.EventRepository
import com.unluckyprayers.associumhub.domain.usecase.CreateEventUseCase
import com.unluckyprayers.associumhub.domain.usecase.UploadEventPosterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EventModule {

    @Provides
    @Singleton
    fun provideEventRepository(
        api: ApiService,
        @ApplicationContext context: Context,
        gson: com.google.gson.Gson
    ): EventRepository = EventRepositoryImpl(api, context, gson)

    @Provides
    @Singleton
    fun provideUploadEventPosterUseCase(
        repository: EventRepository
    ): UploadEventPosterUseCase = UploadEventPosterUseCase(repository)

    @Provides
    @Singleton
    fun provideCreateEventUseCase(
        repository: EventRepository
    ): CreateEventUseCase = CreateEventUseCase(repository)
}
