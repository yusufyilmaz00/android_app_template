package com.unluckyprayers.associumhub.di

import com.unluckyprayers.associumhub.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://pbginizanmtsllleimim.supabase.co/functions/" // ÖRNEK

    @Provides
    @Singleton
    fun provideAuthInterceptor(): Interceptor {
        val supabaseKey = BuildConfig.SUPABASE_KEY
        return Interceptor { chain ->
            val newRequest = chain.request()
                .newBuilder()
                .addHeader("apiKey",supabaseKey)
                .addHeader("Authorization", "Bearer $supabaseKey")
                .build()

            chain.proceed(newRequest)
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}
