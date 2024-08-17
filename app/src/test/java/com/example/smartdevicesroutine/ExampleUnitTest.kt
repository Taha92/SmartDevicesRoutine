package com.example.smartdevicesroutine
import com.example.smartdevicesroutine.Util.DeviceType
import com.example.smartdevicesroutine.model.SmartDevice
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ExampleUnitTest {

    private lateinit var smartDevice: SmartDevice

    @Before
    fun setUp() {
        smartDevice = SmartDevice(
            id = 1,
            name = "Smart Light",
            type = DeviceType.THERMOSTAT.label,
            routine = null,
            isEnabled = true,
            value = "20"
        )
    }

    @Test
    fun testEnableDevice() {
        smartDevice.enable()
        assertEquals(true, smartDevice.isEnabled)
    }

    @Test
    fun testDisableDevice() {
        smartDevice.disable()
        assertEquals(false, smartDevice.isEnabled)
    }

    @Test
    fun testUpdateTemperature() {
        smartDevice.updateValue(DeviceType.THERMOSTAT.label, "24")
        assertEquals("24", smartDevice.value)
    }
}
