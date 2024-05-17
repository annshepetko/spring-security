package ua.kpi.its.lab.security.ui

import MainScreen
import androidx.compose.animation.Crossfade
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import ua.kpi.its.lab.security.ui.login.LoginScreen

@Composable
@Preview
fun App() {
    var token by remember { mutableStateOf("") }
    println("APP TOKEN $token")
    Scaffold(topBar = {
        if (token.isNotBlank()) {
            AppBar()
        }
    }) { innerPadding ->
        Crossfade(
            targetState = token,
            modifier = Modifier.padding(innerPadding)
        ) { t ->
            if (t.isBlank()) {
                LoginScreen { receivedToken ->
                    token = receivedToken // Update token in App
                }
            } else {
                MainScreen(token)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar(
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text("Lab 4")},
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier
    )
}