package cz.vasilevski.sevenguis

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
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlin.math.min
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import org.jetbrains.compose.ui.tooling.preview.Preview

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
                    Tabs.Timer -> TimerProblemWithHotFlow()
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
    var elapsedTime by remember { mutableFloatStateOf(0f) }
    var duration by remember { mutableFloatStateOf(30f) }
    var isRunning by remember { mutableStateOf(true) }

    LaunchedEffect(isRunning) {
        while (isRunning) {
            delay(10)
            if (elapsedTime < duration) {
                elapsedTime += 0.01f
            } else {
                isRunning = false
            }
        }
    }

    Column {
        LinearProgressIndicator(
            progress = min(elapsedTime / duration, 1f),
        )


        Slider(
            value = duration,
            valueRange = 1f..60f,
//            steps = 60 * 10 - 1, // meaning 0.1 s step
            onValueChange = {
                duration = it
                if (elapsedTime < duration && !isRunning) {
                    isRunning = true
                }
            }
        )
        Text(text = duration.toString())

        Divider()

        Button(
            onClick = {
                elapsedTime = 0f
                isRunning = true
            },
            content = { Text("Reset") }
        )
    }
}

@Composable
// reset doesn't restart the countdown, only after I move the slider things start moving again
fun TimerProblemWithFlow() {
    var duration by remember { mutableFloatStateOf(30f) }
    var isRunning by remember { mutableStateOf(true) }
    var resetTrigger by remember { mutableStateOf(0) } // Add this

    // Create timer flow
    val timerFlow =  remember(resetTrigger) {
        flow {
            var elapsedTime = 0f
            while (true) {
                emit(elapsedTime)
                delay(100) // 100ms tick
                if (isRunning && elapsedTime < duration) {
                    elapsedTime += 0.1f
                } else if (elapsedTime >= duration) {
                    isRunning = false
                }
            }
        }
    }

    // Collect the flow as state
    val elapsedTime by timerFlow.collectAsState(initial = 0f)

    Column {
        LinearProgressIndicator(
            progress = min(elapsedTime / duration, 1f),
        )


        Slider(
            value = duration,
            valueRange = 1f..60f,
//            steps = 60 * 10 - 1, // meaning 0.1 s step
            onValueChange = {
                duration = it
                if (elapsedTime < duration && !isRunning) {
                    isRunning = true
                }
            }
        )
        Text(text = duration.toString())

        Divider()

        Button(
            onClick = {
                resetTrigger += 1
                isRunning = true
            },
            content = { Text("Reset") }
        )
    }
}

@Composable
// works but is ugly?
fun TimerProblemWithHotFlow() {
    var duration by remember { mutableFloatStateOf(30f) }
    var isRunning by remember { mutableStateOf(true) }

    val timerFlow = remember {
        MutableSharedFlow<Float>(replay = 1).apply {
            tryEmit(0f)  // Initial value
        }
    }

    // Keep track of elapsed time outside the LaunchedEffect
    val elapsedTimeRef = remember { mutableStateOf(0f) }

    // Start the timer effect
    LaunchedEffect(Unit) {
        while (true) {
            timerFlow.emit(elapsedTimeRef.value)
            delay(100)
            if (isRunning && elapsedTimeRef.value < duration) {
                elapsedTimeRef.value += 0.1f
            } else if (elapsedTimeRef.value >= duration) {
                isRunning = false
            }
        }
    }

    val elapsedTime by timerFlow.collectAsState(initial = 0f)

    Column {
        LinearProgressIndicator(
            progress = min(elapsedTime / duration, 1f),
        )


        Slider(
            value = duration,
            valueRange = 1f..60f,
//            steps = 60 * 10 - 1, // meaning 0.1 s step
            onValueChange = {
                duration = it
                if (elapsedTime < duration && !isRunning) {
                    isRunning = true
                }
            }
        )
        Text(text = duration.toString())

        Divider()

        Button(
            onClick = {
                elapsedTimeRef.value = 0f
                isRunning = true
            },
            content = { Text("Reset") }
        )
    }
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