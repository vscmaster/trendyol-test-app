package com.trendyol.vsh.interview.project.core.di.module

import com.trendyol.vsh.interview.project.core.data.api.KtorTrendyolApiBuilder
import com.trendyol.vsh.interview.project.core.data.api.RetrofitTrendyolApiBuilder
import com.trendyol.vsh.interview.project.core.data.api.TrendyolService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class KtorTrendyolApiService

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RetrofitTrendyolApiService

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @KtorTrendyolApiService
    internal fun provideKtorTrendyolApiService(): TrendyolService {
        return KtorTrendyolApiBuilder.apiService
    }

    @Provides
    @Singleton
    @RetrofitTrendyolApiService
    internal fun provideRetrofitTrendyolApiService(): TrendyolService {
        return RetrofitTrendyolApiBuilder.apiService
    }
}