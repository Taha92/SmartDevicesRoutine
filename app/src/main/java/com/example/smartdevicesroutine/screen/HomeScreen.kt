package com.example.smartdevicesroutine.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.smartdevicesroutine.model.SmartDevice

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: MainViewModel) {
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
    Column {
        LazyColumn {
            items(smartDevices) { device ->
                SmartDeviceItem(device, viewModel)
            }
        }

    }
}

@Composable
fun SmartDeviceItem(device: SmartDevice, viewModel: MainViewModel) {
    Card(modifier = Modifier.padding(8.dp)) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = device.name, style = MaterialTheme.typography.titleMedium)
            Text(text = device.type)
            Switch(checked = device.isEnabled, onCheckedChange = { newStatus ->
                viewModel.updateSmartDevice(device.copy(isEnabled = newStatus))
            })
        }
    }
}
