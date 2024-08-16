package com.example.smartdevicesroutine.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

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
    val routine: Routine,

    @ColumnInfo(name = "value")
    val value: String

): SmartModel(name, isEnabled) {
    override fun performAction() {
        if (isEnabled) {
            println("$name is adjusting the temperature to °C")
        } else {
            println("$name is disabled")
        }
    }
}
