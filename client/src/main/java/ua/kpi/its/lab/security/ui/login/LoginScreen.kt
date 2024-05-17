package ua.kpi.its.lab.security.ui.login

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.expectSuccess
import io.ktor.client.request.basicAuth
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.content.TextContent
import io.ktor.util.InternalAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ua.kpi.its.lab.security.LocalHttpClient

@OptIn(InternalAPI::class)
@Composable
fun LoginScreen(
    updateToken: (String) -> Unit
) {
    val scope = rememberCoroutineScope()
    val client = LocalHttpClient.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var tokenI by remember { mutableStateOf("") } // New state for holding the token
    var error by remember { mutableStateOf("") } // New state for holding errors

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") }
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Button(onClick = {
            scope.launch {
                try {
                    withContext(Dispatchers.IO) {
                        val response =
                            client.post("http://localhost:8080/api/v1/auth/register") {
                                expectSuccess = true
                                basicAuth("username", "password")
                                body = TextContent(
                                    """{"username": "$username", "password": "$password"}""",
                                    contentType = ContentType.Application.Json
                                )
                            }
                        tokenI = response.body()
                    }
                    updateToken(tokenI)
                    println(tokenI)
                } catch (e: ClientRequestException) {
                    error = if (e.response.status.value == 403) {
                        "Invalid username or password. Please try again."
                    } else {
                        "An error occurred: ${e.response.status.description}"
                    }
                } catch (e: Exception) {
                    error = "An unexpected error occurred. Please try again later."
                }
            }
        }) {
            Text("Login")
        }
        if (error.isNotEmpty()) {
            Text(error)
        }
    }
}

@Preview
@Composable
fun LoginPreview() {
    CompositionLocalProvider(
        LocalHttpClient provides HttpClient()
    ) {
        MaterialTheme {
            LoginScreen { }
        }
    }
}
