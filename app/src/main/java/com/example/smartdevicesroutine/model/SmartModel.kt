package com.example.smartdevicesroutine.model

import android.widget.Toast
import androidx.compose.ui.text.toUpperCase
import com.example.smartdevicesroutine.Util.DeviceType

abstract class SmartModel(
    open val name: String,
    open var isEnabled: Boolean
) {

    // Enable the device or service
    open fun enable() {
        isEnabled = true
        println("$name is enabled")
    }

    // Disable the device or service
    open fun disable() {
        isEnabled = false
        println("$name is disabled")
    }

    // Update a generic value (could be overridden by specific smart devices/services)
    open fun updateValue(deviceType: String, value: Any): String {
        if (deviceType.equals(DeviceType.THERMOSTAT.name, true)) {
            println("$name temperature set to $value°C")
            return "$name temperature set to $value°C"
            //println("$name temperature set to $value°C")
        }

        if (deviceType == DeviceType.BULB.name) {
            println("$name brightness set to $value%")
        }

        if (deviceType == DeviceType.WEATHER.name) {
            println("$name updated to $value")
        }


        return ""
        //println("$name updated with value: $value")
    }

    // Abstract function to define the specific behavior of each smart model
    abstract fun performAction()
}
