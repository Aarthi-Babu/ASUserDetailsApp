package com.example.asuserdetailsapp.di

import android.content.Context
import com.example.asuserdetailsapp.BuildConfig
import com.example.asuserdetailsapp.service.UserDetailRepository
import com.example.asuserdetailsapp.service.UserDetailService
import com.example.asuserdetailsapp.utils.ProgressDialog
import dagger.Module
import dagger.Provides
import dagger.assisted.AssistedFactory
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val baseUrl = "http://178.128.151.220/funnzy/public/api/user/"

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient())
            .build()
    }

    @Singleton
    @Provides
    fun provideService(): UserDetailService {
        return provideRetrofit().create(UserDetailService::class.java)
    }

    @Singleton
    @Provides
    fun provideRepo(): UserDetailRepository {
        return UserDetailRepository(provideService())
    }

    @Singleton
    @Provides
    fun provideLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(provideLogger()).build()
    }

    @AssistedFactory
    interface ProgressDialogFactory {
        fun create(context: Context?): ProgressDialog
    }
}