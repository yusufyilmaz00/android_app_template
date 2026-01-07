package com.unluckyprayers.associumhub.di

import android.content.Context
import com.google.gson.Gson
import com.unluckyprayers.associumhub.BuildConfig
import com.unluckyprayers.associumhub.data.local.pref.SharedPrefManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideAuthInterceptor(@ApplicationContext context: Context): Interceptor {
        val supabaseKey = BuildConfig.SUPABASE_KEY
        return Interceptor { chain ->
            val sharedPref = SharedPrefManager(context)
            val userToken = sharedPref.getStringData("accessToken")

            val requestBuilder = chain.request().newBuilder()

            requestBuilder.addHeader("apiKey", supabaseKey)

            if (!userToken.isNullOrEmpty()) {
                val finalToken = if (userToken.startsWith("Bearer ")) userToken else "Bearer $userToken"
                requestBuilder.addHeader("Authorization", finalToken)
            }

            chain.proceed(requestBuilder.build())
        }
    }
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Header ve Body dahil tüm bilgileri loglar
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor) // HTTP trafiğini loglar
            .build()

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
}
