package com.ssverma.covidtracker.di

import com.ssverma.covidtracker.BuildConfig
import com.ssverma.covidtracker.api.NovelCovidApiEndPoints
import com.ssverma.covidtracker.di.annotation.ApplicationScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    fun provideBaseUrl(): String {
        return BuildConfig.BASE_URL
    }

    @ApplicationScope
    @Provides
    fun provideRetrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @ApplicationScope
    @Provides
    fun provideHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @ApplicationScope
    @Provides
    fun provideLogginInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @ApplicationScope
    @Provides
    fun provideApiService(retrofit: Retrofit): NovelCovidApiEndPoints {
        return retrofit.create(NovelCovidApiEndPoints::class.java)
    }

}