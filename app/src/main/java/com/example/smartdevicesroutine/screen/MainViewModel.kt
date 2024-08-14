package com.example.smartdevicesroutine.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartdevicesroutine.model.SmartDevice
import com.example.smartdevicesroutine.repository.SmartDeviceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: SmartDeviceRepository
): ViewModel() {

    private val _smartDevices = MutableStateFlow<List<SmartDevice>>(emptyList())
    val smartDevices = _smartDevices.asStateFlow()
    var loading = MutableStateFlow(false)

    init {
        viewModelScope.launch {
            getSmartDevices()
        }
    }

    fun getSmartDevices() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllDevices().distinctUntilChanged()
                .collect { listOfDevices ->
                    if (listOfDevices.isEmpty()) {
                        Log.d("Empty", ": Empty list")
                    }else {
                        loading.value = false
                        _smartDevices.value = listOfDevices
                    }

                }
        }
    }

    /*val smartDevices = repository.getAllDevices().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList()
    )*/

    fun addSmartDevice(device: SmartDevice) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertDevice(device)
        }
    }

    fun updateSmartDevice(device: SmartDevice) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateDevice(device)
        }
    }
}