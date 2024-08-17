package com.example.smartdevicesroutine.screen

import android.widget.Toast
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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.smartdevicesroutine.Util.DeviceType
import com.example.smartdevicesroutine.model.Routine
import com.example.smartdevicesroutine.model.SmartDevice
import com.example.smartdevicesroutine.model.SmartModel
import com.example.smartdevicesroutine.navigation.DevicesAppScreens
import java.util.Collections


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: MainViewModel) {
    val smartDevices = viewModel.smartDevices.collectAsState().value

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Smart Device Routines") }
        )
    },
        floatingActionButton = {
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
fun HomeContent(routineList: List<SmartDevice>, viewModel: MainViewModel) {
    var showModal by remember { mutableStateOf(false) }
    var selectedRoutine by remember { mutableStateOf<SmartDevice?>(null) }
    // Reverse the list to get the latest at top
    Collections.reverse(routineList)

    Column(modifier = Modifier
        .fillMaxWidth()
    ) {
        LazyColumn {
            items(routineList) { routine ->
                SmartDeviceRoutineItem(routine, viewModel, onRoutineClick = {
                    selectedRoutine = routine
                    showModal = true
                })
            }
        }
    }

    // Bottom modal for editing routine details
    if (showModal && selectedRoutine != null) {

        BottomModal(
            routine = selectedRoutine!!,
            onDismiss = { showModal = false },
            onSave = { updatedRoutine ->
                // Update the routine in database
                viewModel.updateSmartDevice(updatedRoutine)
                showModal = false
            }
        )
    }
}

@Composable
fun SmartDeviceRoutineItem(
    device: SmartDevice,
    viewModel: MainViewModel,
    onRoutineClick: () -> Unit
) {
    var isEnabled by remember { mutableStateOf(device.isEnabled) }
    val context = LocalContext.current
    val valueType = if (device.type.equals(DeviceType.THERMOSTAT.label, true) || device.type.equals(DeviceType.AIR_CONDITIONER.label, true)) {
        "${device.value}°C"
    } else if (device.type.equals(DeviceType.BULB.label, true) || device.type.equals(DeviceType.SMART_WATCH.label, true)) {
        "${device.value}%"
    } else {
        device.value
    }

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable { onRoutineClick() }
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = device.routine!!.name, style = MaterialTheme.typography.bodyMedium)
            Text(text = device.routine.description, style = MaterialTheme.typography.titleMedium)
            Text(text = device.name)
            Text(text = valueType)

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    device.performAction()
                    Toast.makeText(context, device.performAction(), Toast.LENGTH_SHORT).show()
                }) {
                    Text(text = "Perform Action")
                }
                Switch(checked = isEnabled, onCheckedChange = { newStatus ->
                    isEnabled = newStatus
                    if (newStatus) {
                        Toast.makeText(context, device.enable(), Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, device.disable(), Toast.LENGTH_SHORT).show()
                    }
                    viewModel.updateSmartDevice(device.copy(isEnabled = newStatus))
                })
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
    var routineName by remember { mutableStateOf(routine.routine!!.description) }
    var routineValue by remember { mutableStateOf(routine.value) }
    val context = LocalContext.current

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
                    maxLines = 1,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    value = routineName,
                    onValueChange = { routineName = it },
                    label = { Text("Description") }
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .padding(start = 6.dp, end = 6.dp)
                        .fillMaxWidth(),
                    maxLines = 1,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    value = routineValue,
                    onValueChange = { routineValue = it },
                    label = { Text("Value") }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                    onClick = {
                        // Save the updated routine
                        val updatedRoutine = routine.copy(
                            value = routineValue,
                            routine = routine.routine!!.copy(description = routineName)
                        )
                        onSave(updatedRoutine)
                        Toast.makeText(context, routine.updateValue(routine.type, routineValue), Toast.LENGTH_SHORT).show()
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
