package com.example.smartdevicesroutine.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.smartdevicesroutine.model.SmartDevice

@Database(entities = [SmartDevice::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun smartDeviceDao(): SmartDeviceDao
}