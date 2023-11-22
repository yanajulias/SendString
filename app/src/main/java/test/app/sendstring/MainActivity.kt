package test.app.sendstring

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import test.app.sendstring.ui.theme.SendStringTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SendStringTheme {
                SendStringApp()
            }
        }
    }
}

class ServiceManager {
    fun startService(callback: (String) -> Unit) {
        val data = "Ini data dari service"
        callback(data)
    }
}

@Composable
fun SendStringApp() {
    var dataReceived by remember { mutableStateOf("Tidak ada data") }
    val serviceManager = ServiceManager()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                serviceManager.startService {
                    dataReceived = it
                }
            }
        ) {
            Text(text = "Start Service")
        }

        Text(text = dataReceived)
    }
}

@Preview(showSystemUi = true)
@Composable
fun GreetingPreview() {
    SendStringTheme {
        SendStringApp()
    }
}