package ua.kpi.its.lab.security.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ua.kpi.its.lab.security.LocalHttpClient

@Composable
fun LoginScreen(
    updateToken: (String) -> Unit
) {
    val scope = rememberCoroutineScope()
    val client = LocalHttpClient.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        // Username field
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") }
        )
        // Password field (use password masking)
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") }
        )

        Button(onClick = {
            scope.launch {
                try {
                    val token = withContext(Dispatchers.IO) {
                        client.post("http://localhost:8080/token") {
                            expectSuccess = true
                            basicAuth(username, password) // Use entered username and password
                        }
                        // Parse the response and extract the token
                        // Handle potential exceptions here (e.g., throw ClientRequestException)
                        "real_token" // Replace with actual token retrieval logic
                    }
                    updateToken(token)
                } catch (e: Exception) {
                    // Handle exceptions like ClientRequestException and display error messages
                }
            }
        }) {
            Text("Login")
        }
    }
}