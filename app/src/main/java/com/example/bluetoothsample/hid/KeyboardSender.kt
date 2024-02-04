// com.example.bluetoothsample.hid.KeyboardSender

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothHidDevice

@Suppress("MemberVisibilityCanBePrivate")
open class KeyboardSender(
    val hidDevice: BluetoothHidDevice,
    val host: BluetoothDevice
) {
    // Implementation for sending keyboard keys
}



//use room with database, ==> ico {key, modifier{shift, alt...}, on mouse off}