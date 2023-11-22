package test.app.sendstring

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

interface DataCallback {
    fun onDataReceived(data: String)
}

@Composable
fun SendStringApp() {
    val context = LocalContext.current
    val receivedData = remember { mutableStateOf("") }
    val serviceStarted = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (!serviceStarted.value) {
            Button(
                onClick = {
                    serviceStarted.value = true
                    Toast.makeText(context, "Service sudah aktif", Toast.LENGTH_SHORT).show()
                }
            ) {
                Text(text = "Start Service")
            }
        } else {
            DataServiceContent(callback = object : DataCallback {
                override fun onDataReceived(data: String) {
                    receivedData.value = data
                }
            })
        }

        if (receivedData.value.isNotBlank()) {
            ShowReceivedData(receivedData.value)
        }
    }
}

@Composable
fun DataServiceContent(callback: DataCallback) {
    ServiceContent(callback)
}

@Composable
fun ShowReceivedData(data: String) {
    Text(
        text = "Data yang diterima dari service: $data",
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun ServiceContent(callback: DataCallback) {
    Column {
        Button(
            onClick = {
                callback.onDataReceived("Ini data yang dikirim dari service")
            }
        ) {
            Text(text = "Kirim Data ke Activity")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun GreetingPreview() {
    SendStringTheme {
        SendStringApp()
    }
}