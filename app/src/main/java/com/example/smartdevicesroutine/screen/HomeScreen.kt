package com.example.smartdevicesroutine.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.smartdevicesroutine.model.Routine
import com.example.smartdevicesroutine.model.SmartDevice
import com.example.smartdevicesroutine.model.SmartModel
import com.example.smartdevicesroutine.navigation.DevicesAppScreens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: MainViewModel) {
    val smartDevices = viewModel.smartDevices.collectAsState().value

    //val routines by remember { mutableStateOf(viewModel.routines) }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Smart Devices") }
        )
    },
        floatingActionButton = {
            // Add Routine Button
            FloatingActionButton(onClick = {
                // Navigate to Routine Creation Screen
                navController.navigate(DevicesAppScreens.AddRoutineScreen.name)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Routine")
            }
        }
    ) {
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(top = it.calculateTopPadding(), start = 3.dp, end = 3.dp, bottom = 3.dp)
        ) {
            HomeContent(smartDevices, viewModel)
        }
    }
}

@Composable
fun HomeContent(smartDevices: List<SmartDevice>, viewModel: MainViewModel) {
    var showModal by remember { mutableStateOf(false) }
    var selectedRoutine by remember { mutableStateOf<SmartDevice?>(null) }

    Column(modifier = Modifier
        .fillMaxWidth()
    ) {
        LazyColumn {
            items(smartDevices) { device ->
                SmartDeviceItem(device, viewModel, onRoutineClick = {
                    selectedRoutine = device
                    showModal = true
                })
            }
        }
    }

    // Full-screen modal for editing routine details
    if (showModal && selectedRoutine != null) {
        /*EditRoutineModal(
            routine = selectedRoutine!!,
            onDismiss = { showModal = false },
            onSave = { updatedRoutine ->
                // Update the routine in your list or database
                showModal = false
            }
        )*/

        BottomModal(
            routine = selectedRoutine!!,
            onDismiss = { showModal = false },
            onSave = { updatedRoutine ->
                // Update the routine in your list or database
                showModal = false
            }
        )
    }
}

@Composable
fun SmartDeviceItem(
    device: SmartDevice,
    viewModel: MainViewModel,
    onRoutineClick: () -> Unit
) {
    val context = LocalContext.current

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable { onRoutineClick() }
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = device.routine.name, style = MaterialTheme.typography.bodyMedium)
            Text(text = device.routine.description, style = MaterialTheme.typography.titleMedium)
            Text(text = device.name)

            //device.performAction()
            //Toast.makeText(context, device.updateValue(device.type, 22), Toast.LENGTH_SHORT).show()

            Switch(checked = device.isEnabled, onCheckedChange = { newStatus ->
                if (newStatus) {
                    device.enable()
                } else {
                    device.disable()
                }
                viewModel.updateSmartDevice(device.copy(isEnabled = newStatus))
            })
        }
    }
}




class SmartThermostat(id: Int, name: String, isEnabled: Boolean, var temperature: Int) : SmartModel(name, isEnabled) {

    // Update temperature
    fun updateTemperature(newTemperature: Int) {
        temperature = newTemperature
        println("$name temperature set to $newTemperature°C")
    }

    override fun performAction() {
        if (isEnabled) {
            println("$name is adjusting the temperature to $temperature°C")
        } else {
            println("$name is disabled")
        }
    }
}

@Composable
fun EditRoutineModal(
    routine: SmartDevice,
    onDismiss: () -> Unit,
    onSave: (Routine) -> Unit
) {
    var routineName by remember { mutableStateOf(routine.name) }

    // Editable fields for the routine's properties
    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Edit Routine", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = routineName,
                    onValueChange = { routineName = it },
                    label = { Text("Routine Name") }
                )

                // Add more fields for editing actions, times, etc.

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = { onDismiss() }) {
                        Text(text = "Cancel")
                    }
                    Button(onClick = {
                        // Save the updated routine
                        //val updatedRoutine = routine.copy(name = routineName)
                        //onSave(updatedRoutine)
                    }) {
                        Text(text = "Save")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomModal(
    routine: SmartDevice,
    onDismiss: () -> Unit,
    onSave: (SmartDevice) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    var routineName by remember { mutableStateOf(routine.routine.description) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            sheetState = sheetState,
            containerColor = Color.White,
            contentColor = Color.Black,
            content = {
                Text(modifier = Modifier
                    .padding(start = 6.dp, end = 6.dp),
                    text = "Edit Routine",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .padding(start = 6.dp, end = 6.dp)
                        .fillMaxWidth(),
                    value = routineName,
                    onValueChange = { routineName = it },
                    label = { Text("Description") }
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .padding(start = 6.dp, end = 6.dp)
                        .fillMaxWidth(),
                    value = "Value",
                    onValueChange = { /*routineName = it*/ },
                    label = { Text("Value") }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                    onClick = {
                        // Save the updated routine
                        val updatedRoutine = routine.copy(name = routineName)
                        onSave(updatedRoutine)
                    }) {
                    Text(
                        text = "Save",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        )
    }
}
