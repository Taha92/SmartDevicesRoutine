package com.example.smartdevicesroutine.di

import android.content.Context
import androidx.room.Room
import com.example.smartdevicesroutine.room.AppDatabase
import com.example.smartdevicesroutine.room.SmartDeviceDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase
            = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "smart_device_db")
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideNotesDao(appDatabase: AppDatabase): SmartDeviceDao
            = appDatabase.smartDeviceDao()
}