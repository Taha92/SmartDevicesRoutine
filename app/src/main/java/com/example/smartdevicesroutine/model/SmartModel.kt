package com.example.smartdevicesroutine.model

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
    open fun updateValue(value: Any) {
        println("$name updated with value: $value")
    }

    // Abstract function to define the specific behavior of each smart model
    abstract fun performAction()
}
