package com.example.smartdevicesroutine

import com.example.smartdevicesroutine.Util.DeviceType
import com.example.smartdevicesroutine.model.Routine
import com.example.smartdevicesroutine.model.SmartDevice
import com.example.smartdevicesroutine.repository.SmartDeviceRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class RepositoryTest {

    private lateinit var repository: SmartDeviceRepository
    private lateinit var fakeDao: FakeSmartDeviceDao

    @Before
    fun setUp() {
        fakeDao = FakeSmartDeviceDao()
        repository = SmartDeviceRepository(fakeDao)

        val routine = Routine(
            name = "Morning",
            description = "Change temperature",
            startTime = "00:00",
            endTime = "00:00",
        )
        val smartDevice = SmartDevice(
            id = 1,
            name = "Routine",
            type = DeviceType.THERMOSTAT.label,
            isEnabled = true,
            routine = routine,
            value = "30"
        )

        val routines = mutableListOf<SmartDevice>()
        routines.add(smartDevice)

        runBlocking { repository.insertDevice(device = smartDevice) }
    }


    @Test
    fun testInsertDevice() {

        val devices = repository.getAllDevices()
        runBlocking {
            devices.collect {
                assertEquals(true, it.size > 0)
            }
        }
    }

    @Test
    fun testUpdateDevice() {
        val devices = repository.getAllDevices()

        runBlocking {
            devices.collect { devicesList ->
                devicesList.first().isEnabled = false

                val updatedRoutine = devicesList.first().copy(
                    value = "12",
                )
                repository.updateDevice(updatedRoutine)

                assertEquals(false, devicesList.first().isEnabled)

            }
        }
    }

    @Test
    fun testDeleteDevice() {
        val devices = repository.getAllDevices()

        runBlocking {
            devices.collect { devicesList ->

                repository.deleteDevice(devicesList.first())

                assertEquals(0, devicesList.size)

            }
        }
    }
}