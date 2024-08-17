package com.example.smartdevicesroutine.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "routine")
data class Routine(
    @ColumnInfo(name = "routine_name")
    val name: String,  // e.g., "Morning Routine"

    @ColumnInfo(name = "description")
    val description: String,  // e.g., "Turn on thermostat and lights at 7 AM"

    @ColumnInfo(name = "start_time")
    val startTime: String,  // e.g., 7 AM

    @ColumnInfo(name = "end_time")
    val endTime: String? = null  // Optional end time
)
