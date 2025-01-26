package cz.vasilevski.sevenguis

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import sevenguis.composeapp.generated.resources.Res
import sevenguis.composeapp.generated.resources.compose_multiplatform

enum class Tabs(val text: String) {
    Counter("Counter"),
    TemperatureConverter("Temperature Converter"),
    FlightBooker("Flight Booker"),
    Timer("Timer"),
    CRUD("CRUD"),
    CircleDrawer("Circle Drawer"),
    Cells("Cells")
}

@Composable
@Preview
fun App() {
    var selectedTab: Tabs by remember { mutableStateOf(Tabs.Counter) }

    MaterialTheme {
        Row(modifier = Modifier.fillMaxSize()) {
            // Sidebar
            Card(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(250.dp),
                elevation = 4.dp
            ) {
                Column {
                    Text(
                        "7GUIs Problems",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(16.dp)
                    )

                    Divider()

                    // Tabs
                    Tabs.entries.forEach { tab ->
                        TextButton(
                            onClick = { selectedTab = tab },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(
                                tab.text,
                                color = if (selectedTab == tab)
                                    MaterialTheme.colors.primary
                                else
                                    MaterialTheme.colors.onSurface
                            )
                        }
                    }
                }
            }

            // Content area
            Box(modifier = Modifier.weight(1f).padding(16.dp)) {
                when (selectedTab) {
                    Tabs.Counter -> CounterProblem()
                    Tabs.TemperatureConverter -> TemperatureConverterProblem()
                    Tabs.FlightBooker -> FlightBookerProblem()
                    Tabs.Timer -> TimerProblem()
                    Tabs.CRUD -> CRUDProblem()
                    Tabs.CircleDrawer -> CircleDrawerProblem()
                    Tabs.Cells -> CellsProblem()
                }
            }
        }
    }
}

@Composable
fun CounterProblem() {
    var count by remember { mutableStateOf(0) }

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Counter count: $count")
        Button(onClick = {
            count++
        }, content = {
            Text("Click to increment")
        })
    }
}

@Composable
fun TemperatureConverterProblem() {
    var celsius by remember { mutableStateOf(0.0) }
    var fahrenheit by remember { mutableStateOf(celsius2fahrenheit(celsius)) }

    Column {
        TextField(
            value = celsius.toString(),
            onValueChange = {
                celsius = it.toDouble()
                fahrenheit = celsius2fahrenheit(it.toDouble())
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
            ),
            label = { Text("Celsius") },
        )
        Divider()
        TextField(
            value = fahrenheit.toString(),
            onValueChange = {
                fahrenheit = it.toDouble()
                celsius = fahrenheit2celsius(it.toDouble())
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
            ),
            label = { Text("Fahrenheit") },
        )
    }
}

fun celsius2fahrenheit(celsius: Double): Double = (celsius * 9/5) + 32

fun fahrenheit2celsius(fahrenheit: Double): Double = (fahrenheit - 32) * 5/9

enum class FlightType(val text: String) {
    OneWay("One-way"),
    Return("return"),
}

@Composable
fun FlightBookerProblem() {
    var flightType: FlightType by remember { mutableStateOf(FlightType.OneWay) }
    var departureDate: String by remember { mutableStateOf("01.01.2025") }
    var returnDate: String? by remember { mutableStateOf(null) }

    var expanded by remember { mutableStateOf(false) }
    val dropdownOptions = FlightType.entries

    Column {
        Box {
            Button(onClick = { expanded = true }) {
                Text(flightType.text)
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                dropdownOptions.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            flightType = option
                            expanded = false
                        }
                    ) {
                        Text(option.text)
                    }
                }
            }
        }

        Divider()

        TextField(
            value = departureDate,
            onValueChange = { departureDate = it },
            label = { Text("Departure Date") },
        )

        Divider()

//        AnimatedVisibility(flightType == FlightType.Return) {
            TextField(
                value = returnDate.orEmpty(),
                onValueChange = { returnDate = it },
                label = { Text("Return Date") },
                enabled = flightType == FlightType.Return,
            )
//        }
    }
}

@Composable
fun TimerProblem() {
    Text("Timer Problem Content")
}

@Composable
fun CRUDProblem() {
    Text("CRUD Problem Content")
}

@Composable
fun CircleDrawerProblem() {
    Text("Circle Drawer Problem Content")
}

@Composable
fun CellsProblem() {
    Text("Cells Problem Content")
}