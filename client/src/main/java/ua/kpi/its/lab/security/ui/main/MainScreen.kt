import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.plugins.expectSuccess
import io.ktor.client.request.basicAuth
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.content.TextContent
import io.ktor.http.contentType
import io.ktor.http.headers
import io.ktor.util.InternalAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ua.kpi.its.lab.security.dto.ExampleDto

val mockTrains = listOf(
    Train(
        id = "1",
        model = "Express",
        manufacturer = "Train Co.",
        type = "High-speed",
        dateInService = "2023-05-15",
        seatCount = 200,
        weight = 5000,
        hasAC = true,
        route = Route(
            startPoint = "City A",
            endPoint = "City B",
            departureDate = "2024-05-20",
            distance = 500,
            ticketPrice = 50.0,
            isRoundTrip = false
        )
    ),
    Train(
        id = "2",
        model = "Local",
        manufacturer = "Local Train Inc.",
        type = "Commuter",
        dateInService = "2022-12-01",
        seatCount = 100,
        weight = 3000,
        hasAC = false,
        route = Route(
            startPoint = "City B",
            endPoint = "City C",
            departureDate = "2024-05-25",
            distance = 300,
            ticketPrice = 20.0,
            isRoundTrip = true
        )
    )
)

@Composable
fun MainScreen(token: String) {
    val scope = rememberCoroutineScope()
    val client = remember { HttpClient() }
    var items by remember { mutableStateOf(mockTrains) }
    var isLoading by remember { mutableStateOf(false) }
    var showTrainDialog by remember { mutableStateOf(false) }
    var showRouteDialog by remember { mutableStateOf(false) }
    var currentItem by remember { mutableStateOf<Train?>(null) }

    LaunchedEffect(Unit) {
        fetchItems(token, client) {
            items = it
            isLoading = false
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        if (isLoading) {
            CircularProgressIndicator()
        } else if (items.isEmpty()) {
            Text("No trains found")
        } else {
            LazyColumn {
                items(items) { item ->
                    TrainCard(item, onEdit = {
                        currentItem = it
                        showTrainDialog = true
                    }, onDelete = {
                        scope.launch {
                            deleteItem(token, client, it.id!!)
                            fetchItems(token, client) {
                                items = it
                            }
                        }
                    })
                }
            }
        }

        FloatingActionButton(
            onClick = {
                currentItem = null
                showTrainDialog = true
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("+")
        }
    }

    if (showTrainDialog) {
        TrainDialog(
            train = currentItem,
            onSave = { train ->
                scope.launch {
                    if (train.id == null) {
                        createItem(token, client, train)
                    } else {
                        updateItem(token, client, train)
                    }
                    fetchItems(token, client) {
                        items = it
                        showTrainDialog = false
                    }
                }
            },
            onDismiss = { showTrainDialog = false },
            onEditRoute = {
                showTrainDialog = false
                showRouteDialog = true
            }
        )
    }

    if (showRouteDialog) {
        RouteDialog(
            route = currentItem?.route,
            onSave = { route ->
                currentItem = currentItem?.copy(route = route)
                showRouteDialog = false
                showTrainDialog = true
            },
            onDismiss = {
                showRouteDialog = false
                showTrainDialog = true
            }
        )
    }
}

@Composable
fun TrainCard(train: Train, onEdit: (Train) -> Unit, onDelete: (Train) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(train.model)
                Text(train.manufacturer)
                Text("${train.route.startPoint} - ${train.route.endPoint}")
            }
            Row {
                IconButton(onClick = { onEdit(train) }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = { onDelete(train) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    }
}

@Composable
fun TrainDialog(
    train: Train?,
    onSave: (Train) -> Unit,
    onDismiss: () -> Unit,
    onEditRoute: () -> Unit
) {
    var model by remember { mutableStateOf(train?.model ?: "") }
    var manufacturer by remember { mutableStateOf(train?.manufacturer ?: "") }
    var type by remember { mutableStateOf(train?.type ?: "") }
    var dateInService by remember { mutableStateOf(train?.dateInService ?: "") }
    var seatCount by remember { mutableStateOf(train?.seatCount?.toString() ?: "") }
    var weight by remember { mutableStateOf(train?.weight?.toString() ?: "") }
    var hasAC by remember { mutableStateOf(train?.hasAC ?: false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (train == null) "Add Train" else "Edit Train") },
        text = {
            Column {
                TextField(
                    value = model,
                    onValueChange = { model = it },
                    label = { Text("Model") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )
                TextField(
                    value = manufacturer,
                    onValueChange = { manufacturer = it },
                    label = { Text("Manufacturer") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )
                TextField(
                    value = type,
                    onValueChange = { type = it },
                    label = { Text("Type") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )
                TextField(
                    value = dateInService,
                    onValueChange = { dateInService = it },
                    label = { Text("Date in Service") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )
                TextField(
                    value = seatCount,
                    onValueChange = { seatCount = it },
                    label = { Text("Seat Count") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )
                TextField(
                    value = weight,
                    onValueChange = { weight = it },
                    label = { Text("Weight") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Air Conditioning")
                    Checkbox(
                        checked = hasAC,
                        onCheckedChange = { hasAC = it }
                    )
                }
                Divider(modifier = Modifier.padding(vertical = 8.dp))

                Button(onClick = onEditRoute, modifier = Modifier.fillMaxWidth()) {
                    Text("Edit Route")
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                onSave(
                    Train(
                        id = train?.id,
                        model = model,
                        manufacturer = manufacturer,
                        type = type,
                        dateInService = dateInService,
                        seatCount = seatCount.toIntOrNull() ?: 0,
                        weight = weight.toIntOrNull() ?: 0,
                        hasAC = hasAC,
                        route = train?.route ?: Route("", "", "", 0, 0.0, false)
                    )
                )
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun RouteDialog(route: Route?, onSave: (Route) -> Unit, onDismiss: () -> Unit) {
    var startPoint by remember { mutableStateOf(route?.startPoint ?: "") }
    var endPoint by remember { mutableStateOf(route?.endPoint ?: "") }
    var departureDate by remember { mutableStateOf(route?.departureDate ?: "") }
    var distance by remember { mutableStateOf(route?.distance?.toString() ?: "") }
    var ticketPrice by remember { mutableStateOf(route?.ticketPrice?.toString() ?: "") }
    var isRoundTrip by remember { mutableStateOf(route?.isRoundTrip ?: false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (route == null) "Add Route" else "Edit Route") },
        text = {
            Column {
                TextField(
                    value = startPoint,
                    onValueChange = { startPoint = it },
                    label = { Text("Start Point") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )
                TextField(
                    value = endPoint,
                    onValueChange = { endPoint = it },
                    label = { Text("End Point") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )
                TextField(
                    value = departureDate,
                    onValueChange = { departureDate = it },
                    label = { Text("Departure Date") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )
                TextField(
                    value = distance,
                    onValueChange = { distance = it },
                    label = { Text("Distance") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )
                TextField(
                    value = ticketPrice,
                    onValueChange = { ticketPrice = it },
                    label = { Text("Ticket Price") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Round Trip")
                    Checkbox(
                        checked = isRoundTrip,
                        onCheckedChange = { isRoundTrip = it }
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                onSave(
                    Route(
                        startPoint = startPoint,
                        endPoint = endPoint,
                        departureDate = departureDate,
                        distance = distance.toIntOrNull() ?: 0,
                        ticketPrice = ticketPrice.toDoubleOrNull() ?: 0.0,
                        isRoundTrip = isRoundTrip
                    )
                )
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

data class Train(
    val id: String? = null,
    val model: String,
    val manufacturer: String,
    val type: String,
    val dateInService: String,
    val seatCount: Int,
    val weight: Int,
    val hasAC: Boolean,
    val route: Route
)

data class Route(
    val startPoint: String,
    val endPoint: String,
    val departureDate: String,
    val distance: Int,
    val ticketPrice: Double,
    val isRoundTrip: Boolean
)

data class TrainDto(
    val id: String? = null,
    val model: String,
    val producer: String,
    val type: String,
    val dateOfCommissioning: String,
    val numberOfSeats: Int,
    val weight: Int,
    val hasConditioner: Boolean,
    val routeDtos: List<RouteDto>
)

data class RouteDto(
    val id: Boolean, // Assuming this field is of type Boolean in RouteDto
    val departurePoint: String,
    val destination: String,
    val dateOfShipment: String,
    val mileage: Int,
    val priceOfTicket: Double,
    val isCircular: Boolean
)


fun convertToBackendTrainDto(uiDto: Train): TrainDto {
    return TrainDto(
        id = uiDto.id,
        model = uiDto.model,
        producer = uiDto.manufacturer,
        type = uiDto.type,
        dateOfCommissioning = uiDto.dateInService,
        numberOfSeats = uiDto.seatCount,
        weight = uiDto.weight,
        hasConditioner = uiDto.hasAC,
        routeDtos = listOf(convertToBackendRouteDto(uiDto.route))
    )
}

fun convertToBackendRouteDto(uiRouteDto: Route): RouteDto {
    return RouteDto(
        id = uiRouteDto.isRoundTrip,
        departurePoint = uiRouteDto.startPoint,
        destination = uiRouteDto.endPoint,
        dateOfShipment = uiRouteDto.departureDate,
        mileage = uiRouteDto.distance,
        priceOfTicket = uiRouteDto.ticketPrice,
        isCircular = uiRouteDto.isRoundTrip
    )
}

suspend fun fetchItems(token: String, client: HttpClient, onSuccess: (List<Train>) -> Unit) {

}

@OptIn(InternalAPI::class)
suspend fun createItem(token: String, client: HttpClient, uiTrainDto: Train) {
    val backendTrainDto = convertToBackendTrainDto(uiTrainDto)
    val response: HttpResponse = client.post("http://localhost:8080/api/train") {
        header("Authorization", "Bearer: $token")
        body = TextContent(
            """{"id": "${backendTrainDto.id}", "model": "${backendTrainDto.model}", "producer": "${backendTrainDto.producer}", "type": "${backendTrainDto.type}", "dateOfCommissioning": "${backendTrainDto.dateOfCommissioning}", "numberOfSeats": "${backendTrainDto.numberOfSeats}", "weight": "${backendTrainDto.weight}"}, "hasConditioner": "${backendTrainDto.hasConditioner}"}, "routeDtos": "${backendTrainDto.routeDtos}"}""",
            contentType = ContentType.Application.Json
        )
    }
    println(response)
}

suspend fun updateItem(token: String, client: HttpClient, train: Train) {

}

suspend fun deleteItem(token: String, client: HttpClient, id: String) {

}
