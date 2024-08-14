package com.example.smartdevicesroutine.repository

import com.example.smartdevicesroutine.model.SmartDevice
import com.example.smartdevicesroutine.room.SmartDeviceDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SmartDeviceRepository  @Inject constructor(
    private val smartDeviceDao: SmartDeviceDao
) {
    fun getAllDevices(): Flow<List<SmartDevice>> = smartDeviceDao.getAllDevices()

    suspend fun insertDevice(device: SmartDevice) {
        smartDeviceDao.insertDevice(device)
    }

    suspend fun updateDevice(device: SmartDevice) {
        smartDeviceDao.updateDevice(device)
    }

    suspend fun deleteDevice(device: SmartDevice) {
        smartDeviceDao.deleteDevice(device)
    }
}