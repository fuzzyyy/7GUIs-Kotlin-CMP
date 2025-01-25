package cz.vasilevski.sevenguis

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
    Text("Counter Problem Content")
}

@Composable
fun TemperatureConverterProblem() {
    Text("Temperature Converter Problem Content")
}

@Composable
fun FlightBookerProblem() {
    Text("Flight Booker Problem Content")
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