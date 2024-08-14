package com.example.smartdevicesroutine.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "smart_devices")
data class SmartDevice(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "is_enabled")
    val isEnabled: Boolean,

    @ColumnInfo(name = "value")
    val value: String
)
