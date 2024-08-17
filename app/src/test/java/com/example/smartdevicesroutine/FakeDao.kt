package com.example.smartdevicesroutine

import com.example.smartdevicesroutine.model.SmartDevice
import com.example.smartdevicesroutine.room.SmartDeviceDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeSmartDeviceDao : SmartDeviceDao {
    private val devices = mutableListOf<SmartDevice>()

    override fun getAllDevices(): Flow<List<SmartDevice>> {
        return flow { emit(devices) }
    }

    override suspend fun insertDevice(device: SmartDevice) {
        devices.add(device)
    }

    override suspend fun updateDevice(device: SmartDevice) {
        val index = devices.indexOfFirst { it.id == device.id }
        if (index != -1) {
            devices[index] = device
        }
    }

    override suspend fun deleteDevice(device: SmartDevice) {
        devices.remove(device)
    }
}