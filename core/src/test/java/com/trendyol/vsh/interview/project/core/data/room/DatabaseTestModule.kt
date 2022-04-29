package com.trendyol.vsh.interview.project.core.data.room

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseTestModule {

    @Singleton
    @Provides
    internal fun provideDatabase(@ApplicationContext context: Context): TrendyolDatabase {
        return Room.inMemoryDatabaseBuilder(context, TrendyolDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }
}
