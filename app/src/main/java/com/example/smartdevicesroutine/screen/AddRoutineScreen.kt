package com.example.smartdevicesroutine.screen

import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.smartdevicesroutine.Util.DeviceType
import com.example.smartdevicesroutine.model.Routine
import com.example.smartdevicesroutine.model.SmartDevice
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRoutineScreen(navController: NavController, mainViewModel: MainViewModel) {
    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Add Routine") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back icon"
                    )
                }
            },
        )
    }) {
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(top = it.calculateTopPadding(), start = 3.dp, end = 3.dp, bottom = 3.dp)
        ) {
            AddRoutineContent(mainViewModel, navController)
        }
    }
}


@Composable
fun AddRoutineContent(mainViewModel: MainViewModel, navController: NavController) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf("Option 1") }
    var startTime by remember { mutableStateOf(LocalTime.now()) }
    var endTime by remember { mutableStateOf<LocalTime?>(LocalTime.now()) }
    var isToggled by remember { mutableStateOf(false) }
    var valueLabel by remember { mutableStateOf("") }
    var commandValue by remember { mutableStateOf("") }

    // State for managing the TimePickerDialog visibility
    var showStartTimePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val valid = remember(name, description, commandValue
    ) {
        name.trim().isNotEmpty() && description.trim().isNotEmpty() && commandValue.trim().isNotEmpty()
    }

    // Get label for device type
    valueLabel = getValueLabelByType(selectedOption)

    // Start time dialog
    if (showStartTimePicker) {
        TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                startTime = LocalTime.of(hourOfDay, minute)
                showStartTimePicker = false // Dismiss the dialog after selection
            },
            startTime.hour, startTime.minute, true
        ).show()
    }

    // End time dialog
    if (showEndTimePicker) {
        TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                endTime = LocalTime.of(hourOfDay, minute)
                showEndTimePicker = false // Dismiss the dialog after selection
            },
            endTime!!.hour, endTime!!.minute, true
        ).show()
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .verticalScroll(rememberScrollState())
        .imePadding()
    ) {

        // Routine Title
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Title") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Routine Command (e.g: Set temperature of living room)
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // List of smart devices/services
        SmartDevicesDropDown { value ->
            selectedOption = value
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Value (According to the Device/Service)
        TextField(
            value = commandValue,
            onValueChange = { commandValue = it },
            label = { Text(valueLabel.ifEmpty { "e.g: 25" }) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Start Time Selection
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        TextButton(onClick = { showStartTimePicker = true }) {
            Text(
                "Start Time: ${startTime.format(timeFormatter)}",
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // End Time Selection
        TextButton(onClick = { showEndTimePicker = true }) {
            Text(
                "End Time: ${endTime?.format(timeFormatter) ?: "Not set"}",
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Switch for Toggle
        Row(
            modifier = Modifier
                .padding(start = 10.dp, end = 6.dp, bottom = 6.dp, top = 6.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("On / Off")
            Switch(
                checked = isToggled,
                onCheckedChange = { isToggled = it }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        val routine = Routine(
                name = name,
                description = description,
                startTime = startTime.toString(),
                endTime = endTime.toString()
            )
            val device = SmartDevice(
                name = selectedOption,
                type = selectedOption,
                isEnabled = isToggled,
                routine = routine,
                value = commandValue
            )

        // Save Button
        Button(
            onClick = { saveRoutine(mainViewModel, device, navController) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = valid
        ) {
            Text("Save")
        }
    }
}

@Composable
fun getValueLabelByType(selectedOption: String): String {
    var valueLabel by remember { mutableStateOf("") }

    if (selectedOption.equals(DeviceType.THERMOSTAT.label, true) || selectedOption.equals(DeviceType.AIR_CONDITIONER.label, true)) {
        valueLabel = "e.g: 10 °C"
    } else if (selectedOption.equals(DeviceType.BULB.label, true) || selectedOption.equals(DeviceType.SMART_WATCH.label, true)) {
        valueLabel = "e.g: 25"
    }  else if (selectedOption.equals(DeviceType.NEWS.label, true)) {
        valueLabel = "e.g: World Health Day"
    }  else if (selectedOption.equals(DeviceType.WEATHER.label, true)) {
        valueLabel = "e.g: Cloudy"
    }

    return valueLabel
}

fun saveRoutine(mainViewModel: MainViewModel, device: SmartDevice, navController: NavController) {
    mainViewModel.addSmartDevice(device)
    navController.popBackStack()
}

@Composable
fun SmartDevicesDropDown(
    onTextChange: (String) -> Unit,
) {

    var dropControl by remember { mutableStateOf(false) }
    var selectIndex by remember { mutableIntStateOf(0) }

    val deviceList = listOf(DeviceType.SMART_WATCH.label, DeviceType.BULB.label,
        DeviceType.THERMOSTAT.label, DeviceType.AIR_CONDITIONER.label,
        DeviceType.WEATHER.label, DeviceType.NEWS.label)

    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {

        OutlinedCard(modifier = Modifier
            .fillMaxWidth()
            .clickable { dropControl = true }
        ) {
            Row(horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(15.dp)
            ) {

                Text(text = deviceList[selectIndex])

                Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "Arrow icon")

            }

            DropdownMenu(modifier = Modifier
                .fillMaxWidth(),
                expanded = dropControl,
                onDismissRequest = { dropControl = false}) {

                deviceList.forEachIndexed { index, strings ->
                    DropdownMenuItem(
                        text = {
                            Text(text = strings)
                               },

                        onClick = {
                            dropControl = false
                            selectIndex = index
                            onTextChange.invoke(strings)
                        })
                }
            }
        }
    }
}
