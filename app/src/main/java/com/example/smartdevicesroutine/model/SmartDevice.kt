package com.example.smartdevicesroutine.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.smartdevicesroutine.Util.DeviceType

@Entity(tableName = "smart_devices")
data class SmartDevice(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    @ColumnInfo(name = "name")
    override val name: String, // Morning Routine

    @ColumnInfo(name = "type")
    val type: String, // Device type SMART_WATCH, CAMERA, BULB

    @ColumnInfo(name = "is_enabled")
    override var isEnabled: Boolean,

    @Embedded
    val routine: Routine?,

    @ColumnInfo(name = "value")
    override var value: String = ""

): SmartModel(name, isEnabled, value) {
    override fun performAction(): String {
        if (isEnabled) {
            if (type.equals(DeviceType.THERMOSTAT.label, true) || type.equals(DeviceType.AIR_CONDITIONER.label, true)) {
                println("$name is adjusting the temperature to ${value}°C")
                return "$name is adjusting the temperature to ${value}°C"

            } else if (type.equals(DeviceType.BULB.label, true) || type.equals(DeviceType.SMART_WATCH.label, true)) {
                println("$name is lighting up with brightness ${value}%")
                return "$name is lighting up with brightness ${value}%"

            } else if (type.equals(DeviceType.NEWS.label, true)) {
                println("Fetching today's news: $value from $name")
                return "Fetching today's news: $value from $name"

            } else if (type.equals(DeviceType.WEATHER.label, true)) {
                println("Fetching current weather: $value from $name")
                return "Fetching current weather: $value from $name"
            }

        } else {
            println("$name is disabled")
            return "$name is disabled"
        }

        return ""
    }
}
