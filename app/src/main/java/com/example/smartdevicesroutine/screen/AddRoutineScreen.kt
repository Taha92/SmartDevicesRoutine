package com.example.smartdevicesroutine.screen

import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Switch
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.smartdevicesroutine.model.Routine
import com.example.smartdevicesroutine.model.SmartDevice
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRoutineScreen(navController: NavHostController, mainViewModel: MainViewModel) {
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
    val options = listOf("Option 1", "Option 2", "Option 3")
    var startTime by remember { mutableStateOf(LocalTime.now()) }
    var endTime by remember { mutableStateOf<LocalTime?>(null) }
    var isToggled by remember { mutableStateOf(false) }

    // Dropdown
    var expanded by remember { mutableStateOf(false) }
    var selectIndex by remember { mutableIntStateOf(0) }

    // State for managing the TimePickerDialog visibility
    val context = LocalContext.current
    var showStartTimePicker by remember { mutableStateOf(false) }

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

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {

        // Name (Single line)
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Description (Multi line)
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            maxLines = 2,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropDownFun() { value ->
            selectedOption = value
        }


        Spacer(modifier = Modifier.height(16.dp))

        // Start Time Selection
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        TextButton(onClick = { showStartTimePicker = true }) {
            Text("Start Time: ${startTime.format(timeFormatter)}")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // End Time Selection
        TextButton(onClick = { /* Handle End Time Picker */ }) {
            Text("End Time: ${endTime?.format(timeFormatter) ?: "Not set"}")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Switch for Toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Toggle")
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
                endTime = null
            )
            val device = SmartDevice(
                name = selectedOption,
                type = "Test",
                isEnabled = true,
                routine = routine,
                value = ""
            )

        // Save Button
        Button(
            onClick = { saveRoutine(mainViewModel, device, navController) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save")
        }
    }
}

fun saveRoutine(mainViewModel: MainViewModel, device: SmartDevice, navController: NavController) {
    mainViewModel.addSmartDevice(device)
    navController.popBackStack()
}

@Composable
fun DropDownFun(
    onTextChange: (String) -> Unit,
) {

    var dropControl by remember { mutableStateOf(false) }
    var selectIndex by remember { mutableIntStateOf(0) }
    var countryList = listOf("Turkey", "Germany", "France", "Italy","Canada")

    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {

        OutlinedCard(modifier = Modifier.fillMaxWidth()) {
            Row(horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .wrapContentWidth()
                    .height(50.dp)
                    .padding(15.dp)
                    .clickable {
                        dropControl = true
                    }) {

                Text(text = countryList[selectIndex])

            }

            DropdownMenu(expanded = dropControl, onDismissRequest = { dropControl = false}) {

                countryList.forEachIndexed { index, strings ->
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
