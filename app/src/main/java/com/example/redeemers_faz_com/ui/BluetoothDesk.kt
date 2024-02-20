// com.example.bluetoothsample.ui.BluetoothDesk

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.redeemers_faz_com.BluetoothController

@Composable
fun BluetoothDesk(bluetoothController: BluetoothController) {
    // Implementation for Bluetooth Desk UI
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(imageVector = Icons.Default.Computer, contentDescription = null)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Bluetooth Desk")
            Spacer(modifier = Modifier.height(16.dp))
            BluetoothUiConnection(bluetoothController)
        }
    }
}
