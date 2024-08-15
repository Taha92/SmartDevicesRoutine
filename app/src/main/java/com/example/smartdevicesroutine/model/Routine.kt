package com.example.smartdevicesroutine.model

import androidx.room.ColumnInfo
import androidx.room.Entity

/*data class Routine(
    val id: String,
    val name: String,
    val trigger: String,
    val smartActions: List<String>,
)*/

@Entity(tableName = "routine")
data class Routine(
    @ColumnInfo(name = "routine_name")
    val name: String,  // e.g., "Morning Routine"

    @ColumnInfo(name = "description")
    val description: String,  // e.g., "Turn on thermostat and lights at 7 AM"

    //val isActive: Boolean = true,

    /*@Embedded
    @ColumnInfo(name = "smart_model")
    val smartModel: SmartModel,*/  // Embedded SmartModel for routine automation

    @ColumnInfo(name = "start_time")
    val startTime: String,  // e.g., 7 AM

    @ColumnInfo(name = "end_time")
    val endTime: Long? = null  // Optional end time
)
