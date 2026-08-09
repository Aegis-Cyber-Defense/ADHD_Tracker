package com.aegis.adhdtracker.di

import android.content.Context
import androidx.room.Room
import com.aegis.adhdtracker.data.local.AppDatabase
import com.aegis.adhdtracker.data.local.LogDao
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
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "adhd_tracker_db"
        ).build()
    }

    @Provides
    fun provideLogDao(db: AppDatabase): LogDao = db.logDao()
}
