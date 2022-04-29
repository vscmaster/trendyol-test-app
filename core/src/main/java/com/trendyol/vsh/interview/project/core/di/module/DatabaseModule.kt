package com.trendyol.vsh.interview.project.core.di.module

import android.content.Context
import androidx.room.Room
import com.trendyol.vsh.interview.project.core.data.room.TrendyolDatabase
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
    internal fun provideTrendyolDatabase(@ApplicationContext context: Context): TrendyolDatabase {
        return Room.databaseBuilder(context, TrendyolDatabase::class.java, "trendyol.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}