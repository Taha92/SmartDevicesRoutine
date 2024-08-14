package com.example.smartdevicesroutine.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.smartdevicesroutine.model.SmartDevice
import kotlinx.coroutines.flow.Flow

@Dao
interface SmartDeviceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDevice(device: SmartDevice)

    @Query("SELECT * FROM smart_devices")
    fun getAllDevices(): Flow<List<SmartDevice>>

    @Update
    suspend fun updateDevice(device: SmartDevice)

    @Delete
    suspend fun deleteDevice(device: SmartDevice)
}